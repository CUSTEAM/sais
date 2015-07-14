package action.just;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Message;
import action.BaseAction;
import action.just.justFilder.testPrint;

public class JustFilder extends BaseAction{
	
	public String cno;
	public String tno;
	public String grade;
	public String endDate;	
	public String gradEndKillDate;
	public String gradEnd;
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	private Float dilg;
	private List<Map>dilgs;
	List<Map>dilgRule=df.sqlGet("SELECT id, score FROM Dilg_rules WHERE score>0");
	
	public String execute(){
		
		return SUCCESS;
	}
	
	private List<Map>tmp;
	
	/**
	 * 試算
	 * @return
	 * @throws IOException 
	 */
	public String test() throws IOException{		
		if(checkTotalScore()){
			Message msg=new Message();
			msg.setError("成績已結算");
			this.savMessage(msg);
			return execute();
		}
		
		if(endDate.trim().equals("")){
			Message msg=new Message();
			msg.setError("請點選日期");
			this.savMessage(msg);
			return execute();
		}
		
		leadConut();//建立計算範圍
		setExam();//計算並寫入扣考
			
		List<Map>cls=new ArrayList();//資料容器
		cls=setDetail(tmp, cls, false, false);		
		export(cls);
		//recoverSeld();//復原扣考
		return null;		
	}
	
	/**
	 * 結算
	 * @return
	 * @throws IOException 
	 */
	public String count() throws IOException{
		
		if(endDate.trim().equals("")){
			Message msg=new Message();
			msg.setError("請點選日期");
			this.savMessage(msg);
			return execute();
		}
		
		leadConut();//設定範圍
		if(!checkTotalScore()){
			setExam();//初次結算才計算並寫入扣考
		}	
		
		List<Map>cls=new ArrayList();
		cls=setDetail(tmp, cls, true, false);	
		export(cls);		
		return null;	
	}
	
	/**
	 * 細節計算
	 * @param ClassNo
	 * @param close 是否為結算
	 * @return
	 */
	private List ClassCnt(String ClassNo, boolean close, boolean reCount){		
		//選取操行模版
		List <Map>stds=getStds(ClassNo);
		
		int total_score;	
		for(int i=0; i<stds.size(); i++){
			
			dilg=0f;
			total_score=0;
			//篩選未被標記為扣考的選課 IS NULL
			dilgs=df.sqlGet("SELECT COUNT(*)as cnt, abs FROM Seld s LEFT OUTER JOIN Dilg d ON s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid " +
			"WHERE s.status IS NULL AND d.date<='"+endDate+"' AND d.student_no='"+stds.get(i).get("student_no")+"' GROUP BY d.abs");			
			//計算未扣考的缺課
			for(int k=0; k<dilgs.size(); k++){	
				for(int l=0; l<dilgRule.size(); l++){					
					if(dilgs.get(k).get("abs").equals(dilgRule.get(l).get("id"))){						
						dilg=dilg+(Integer.parseInt(dilgs.get(k).get("cnt").toString())*Float.parseFloat(dilgRule.get(l).get("score").toString()));						
					}
					//曠課超過45節(不含扣考？)					
					if(dilgs.get(k).get("abs").equals("2")&&Integer.parseInt(dilgs.get(k).get("cnt").toString())>=45){
						stds.get(i).put("dilg45", 1);
					}
				}
			}
			
			//結算後不再修正 (重新結算畢 )			
			if(!reCount && stds.get(i).get("total_score")!=null){				
				stds.get(i).putAll(chStd(stds.get(i)));				
				continue;
			}
			
			
			/*篩選未被標記為扣考的選課 IS NULL
			dilgs=df.sqlGet("SELECT COUNT(*)as cnt, abs FROM Seld s LEFT OUTER JOIN Dilg d ON s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid " +
			"WHERE s.status IS NULL AND d.date<='"+endDate+"' AND d.student_no='"+stds.get(i).get("student_no")+"' GROUP BY d.abs");			
			//計算未扣考的缺課
			for(int k=0; k<dilgs.size(); k++){	
				for(int l=0; l<dilgRule.size(); l++){					
					if(dilgs.get(k).get("abs").equals(dilgRule.get(l).get("id"))){						
						dilg=dilg+(Integer.parseInt(dilgs.get(k).get("cnt").toString())*Float.parseFloat(dilgRule.get(l).get("score").toString()));						
					}
					//曠課超過45節(不含扣考？)					
					if(dilgs.get(k).get("abs").equals("2")&&Integer.parseInt(dilgs.get(k).get("cnt").toString())>=45){
						stds.get(i).put("dilg45", 1);
					}
				}
			}
			*/
			stds.get(i).put("dilg_score", 0-Math.round(dilg));
			stds.get(i).putAll(chStd(stds.get(i)));
			
			if(dilg>0.0f){//全勤+3分
				stds.get(i).put("dilg_score_real", String.valueOf(dilg));
			}else{
				stds.get(i).put("dilg_score", 3);
			}
			total_score=82+(
				Integer.parseInt(stds.get(i).get("teacher_score").toString())+
				Integer.parseInt(stds.get(i).get("deptheader_score").toString())+
				Integer.parseInt(stds.get(i).get("military_score").toString())+
				Integer.parseInt(stds.get(i).get("meeting_score").toString())+					
				Integer.parseInt(stds.get(i).get("desd_score").toString())+
				Integer.parseInt(stds.get(i).get("dilg_score").toString())
			);
			
			//高於95分=95分
			if(total_score>95){
				total_score=95;
			}
			stds.get(i).put("total_score", total_score);
			
			//結算的情況
			if(close){				
				saveJust(
				stds.get(i).get("student_no").toString(), 
				total_score, 
				Integer.parseInt(stds.get(i).get("teacher_score").toString()), 
				Integer.parseInt(stds.get(i).get("deptheader_score").toString()), 
				Integer.parseInt(stds.get(i).get("military_score").toString()), 
				Integer.parseInt(stds.get(i).get("meeting_score").toString()), 
				Integer.parseInt(stds.get(i).get("dilg_score").toString()), 
				Integer.parseInt(stds.get(i).get("desd_score").toString()));
			}						
		}
		
		return stds;
	}
	
	/**
	 * 儲存Just
	 * ON DUPLICATE KEY UPDATE
	 */
	public void saveJust(String student_no, int total_score, int teacher_score, 
	int deptheader_score, int military_score, int meeting_score, int dilg_score, int desd_score){
		//以sql效率較佳
		df.exSql("INSERT INTO Just(student_no,total_score,teacher_score,deptheader_score,military_score,meeting_score,dilg_score,desd_score)VALUES" +
		"('"+student_no+"',"+total_score+","+teacher_score+","+deptheader_score+","+military_score+","+meeting_score+","+dilg_score+","+desd_score+")" +
		"ON DUPLICATE KEY UPDATE total_score="+total_score+",teacher_score="+teacher_score+", deptheader_score="+deptheader_score+", " +
		"military_score="+military_score+", dilg_score="+dilg_score+", desd_score="+desd_score);
	}
	
	/**
	 * 將範圍內扣考標記設回null
	 */
	private void recoverSeld(){
		StringBuilder sb=new StringBuilder("UPDATE Seld SET status=null WHERE student_no IN(SELECT student_no FROM " +
		"stmd, Class WHERE stmd.depart_class=Class.ClassNo AND Class.CampusNo='"+cno+"' AND Class.SchoolType='"+tno+"'");
		if(!grade.equals("")){			
			if(grade.equals("0")){//非畢業班
				sb.append("AND Class.graduate='0' AND Class.Type!='E'");
			}else if(grade.equals("1")){//畢業班
				sb.append("AND Class.graduate='1'");
			}else if(grade.equals("2")){//延修班
				sb.append("AND Class.Type='E'");
			}
		}
		sb.append(")");
		df.exSql(sb.toString());//復原Seld標記
	}
	
	/**
	 * 扣考作業
	 * @param close 是否為結算
	 */
	private void setExam(){
		//將範圍內扣考標記設回null
		recoverSeld();
		//標記範圍內扣考，排除不計算扣考的假別
		StringBuilder sb1=new StringBuilder("SELECT d.student_no, d.Dtime_oid, ((dt.thour*18)/3)as thour FROM stmd s, Dilg d, Dtime dt, Class c WHERE " );
		if(grade.equals("0")){//非畢業班
			sb1.append("c.graduate='0'AND c.Type!='E' AND ");
		}else if(grade.equals("1")){//畢業班
			sb1.append("c.graduate='1'AND ");
		}else if(grade.equals("2")){//延修班
			sb1.append("c.Type='E'AND ");
		}
		sb1.append("s.depart_class=c.ClassNo AND c.CampusNo='"+cno+"' AND c.SchoolType='"+tno+"' AND d.date<='"+endDate+"' AND " +
		"s.student_no=d.student_no AND dt.Oid=d.Dtime_oid AND d.abs IN(SELECT id FROM Dilg_rules WHERE exam='1')" +
		"GROUP BY d.student_no, d.Dtime_oid HAVING COUNT(*)>=thour");
		//System.out.println(sb1);
		List<Map>list=df.sqlGet(sb1.toString());
		//建立扣考標記
		for(int i=0; i<list.size(); i++){
			df.exSql("UPDATE Seld SET status='1' WHERE student_no='"+list.get(i).get("student_no")+"' AND Dtime_oid="+list.get(i).get("Dtime_oid"));
		}
		//建立遠距扣考標記
		list=df.sqlGet("SELECT s.student_no, s.Dtime_oid, s.elearn_dilg,((d.thour*18)/3)as thour " +
		"FROM Seld s, stmd st, Dtime d, Class c WHERE s.elearn_dilg>6 AND c.CampusNo='"+cno+"' AND c.SchoolType='"+tno+"'AND " +
		"c.ClassNo=st.depart_class AND d.Oid=s.Dtime_oid AND s.student_no=st.student_no HAVING s.elearn_dilg>=thour");		
		for(int i=0; i<list.size(); i++){
			df.exSql("UPDATE Seld SET status='1' WHERE student_no='"+list.get(i).get("student_no")+"' AND Dtime_oid="+list.get(i).get("Dtime_oid"));
		}
	}
	
	/**
	 * 建立班級Just模版
	 * @param ClassNo
	 * @return
	 */
	private<Map>List getStds(String ClassNo){			
		
		StringBuilder sb=new StringBuilder("SELECT ");
		
		if(grade.equals("1")||grade.equals("2")){//2是延畢
			//畢業生找下修課程數
			sb.append("(SELECT COUNT(*) FROM Seld, Dtime, Class WHERE Seld.Dtime_oid=Dtime.Oid AND " +
					"Class.ClassNo=Dtime.depart_class AND Class.graduate!='1' AND Dtime.Sterm='2' AND Seld.student_no=st.student_no)as down,");
		}
		
		sb.append(" k.down_year, j.com_code1,j.com_code2,com_code3, " +
				"(SELECT SUM(score)FROM desd WHERE desd.student_no=st.student_no GROUP BY st.student_no)as desd_score, st.student_no, " +
				"st.student_name, j.teacher_score, j.deptheader_score,j.military_score, j.dilg_score, j.meeting_score, j.total_score, " +
				"j.com_code1, j.com_code2, j.com_code3 FROM (stmd st LEFT OUTER JOIN Just j ON st.student_no=j.student_no)LEFT OUTER JOIN " +
				"keep k ON k.student_no=st.student_no WHERE st.depart_class='"+ClassNo+"'ORDER BY st.student_no");
		
		return df.sqlGet(sb.toString());
	}
	
	/**
	 * 確認是否已結算
	 * @return
	 */
	private boolean checkTotalScore(){		
		StringBuilder sb=new StringBuilder("SELECT COUNT(*) FROM stmd st, Class c, Just j WHERE " +
		"st.depart_class=c.ClassNo AND st.student_no=j.student_no AND c.CampusNo='"+cno+"' AND " +
		"c.SchoolType='"+tno+"' AND j.total_score IS NOT NULL ");
		if(!grade.equals("")){
			if(grade.equals("0")){
				sb.append("AND c.graduate='0' AND c.Type!='E'");
			}else if(grade.equals("1")){
				sb.append("AND c.graduate='1'");
			}else if(grade.equals("2")){
				sb.append("AND c.Type='E'");
			}
		}
		
		//有1人有總分即回傳true
		if(Integer.parseInt(df.sqlGetStr(sb.toString()))>0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 設定計算範圍
	 */
	private void leadConut(){
		
		//建立班級範圍
		StringBuilder sb=new StringBuilder("SELECT IFNULL(e.cname, '未指定')as cname, (SELECT COUNT(*)FROM stmd WHERE stmd.depart_class=c.ClassNo)as " +
		"cnt, c.ClassNo, c.ClassName FROM Class c LEFT OUTER JOIN empl e ON c.tutor=e.idno WHERE c.CampusNo='"+cno+"' AND c.SchoolType='"+tno+"'");
		
		if(!grade.equals("")){
			if(grade.equals("0")){
				sb.append("AND c.graduate='0' AND c.Type!='E' ");
			}else if(grade.equals("1")){
				sb.append("AND c.graduate='1'");
			}else if(grade.equals("2")){
				sb.append("AND c.Type='E'");
			}
		}
		sb.append(" ORDER BY c.ClassNo");
		tmp=df.sqlGet(sb.toString());
	}	
	
	/**
	 * 寫報表
	 * @param cls
	 * @throws IOException
	 */
	private void export(List cls) throws IOException{		
		cls=setFails(cls);
		Map info=new HashMap();
		info.put("school_year", getContext().getAttribute("school_year"));
		info.put("school_term", getContext().getAttribute("school_term"));
		testPrint p=new testPrint();
		p.print(response, cls, info);		
	}
	
	/**
	 * 資料整理
	 * @param m
	 * @return
	 */
	private Map chStd(Map m){
		if(m.get("teacher_score")==null){m.put("teacher_score", 0);}
		if(m.get("deptheader_score")==null){m.put("deptheader_score", 0);}
		if(m.get("military_score")==null){m.put("military_score", 0);}
		if(m.get("meeting_score")==null){m.put("meeting_score", 0);}
		if(m.get("desd_score")==null){m.put("desd_score", 0);}
		if(m.get("com_code1")==null){m.put("com_code1", "");}
		if(m.get("com_code2")==null){m.put("com_code2", "");}
		if(m.get("com_code3")==null){m.put("com_code3", "");}
		if(m.get("down_year")==null){m.put("down_year", "");}else{m.put("down_year", "*");}
		if(grade.equals("1")){
			if(Integer.parseInt(m.get("down").toString())<1){m.put("down", "");}else{m.put("down", "#");}
		}else{
			m.put("down", "");
		}
		return m;
	}
	
	/**
	 * 重新結算畢業班
	 */
	public String reCount() throws IOException{		
		endDate=gradEnd;		
		if(endDate.trim().equals("")||gradEndKillDate.trim().equals("")){
			Message msg=new Message();
			msg.setError("請點選日期");
			this.savMessage(msg);
			return execute();
		}
		
		if(!grade.equals("1")){
			Message msg=new Message();
			msg.setError("範圍請指定畢業班");
			this.savMessage(msg);
			return execute();
		}
			
		//暫存非畢業班的Dilg
		List<Map>d=df.sqlGet("SELeCT * FROM Dilg WHERE Dtime_oid IN(SELECT Dtime.Oid FROM Dtime, Class WHERE " +
		"Dtime.depart_class=Class.ClassNo AND Class.graduate='1' AND Dtime.Sterm='2')AND date>'"+gradEndKillDate+"'");
		
		//暫時清除非畢業班的Dilg
		df.exSql("DELETE FROM Dilg WHERE Dtime_oid IN(SELECT Dtime.Oid FROM Dtime, Class WHERE " +
		"Dtime.depart_class=Class.ClassNo AND Class.graduate='1' AND Dtime.Sterm='2')AND date>'"+gradEndKillDate+"'");		
		//計算
		leadConut();
		setExam();//計算並寫入扣考
		List<Map>cls=new ArrayList();//資料容器			
		cls=setDetail(tmp, cls, true, true);
		
		//塞回非畢業班的dilg
		for(int i=0; i<d.size(); i++){			
			df.exSql("INSERT INTO Dilg(Oid,student_no,date,cls,abs,Dtime_oid,earlier,Dilg_app_oid)VALUES" +
			"("+d.get(i).get("Oid")+",'"+d.get(i).get("student_no")+"','"+d.get(i).get("date")+"'," +
			"'"+d.get(i).get("cls")+"','"+d.get(i).get("abs")+"',"+d.get(i).get("Dtime_oid")+","+
			d.get(i).get("earlier")+","+d.get(i).get("Dilg_app_oid")+");");
		}
		
		export(cls);		
		return null;
	}
	
	private List setDetail(List<Map>tmp, List cls, boolean close, boolean reCount){
		List stds;
		for(int i=0; i<tmp.size(); i++){
			if(Integer.parseInt(tmp.get(i).get("cnt").toString())>0){
				tmp.get(i).put("stds", ClassCnt(tmp.get(i).get("ClassNo").toString(), close, reCount));	
				cls.add(tmp.get(i));
			}	
		}		
		return cls;
	}
	
	/**
	 * 其他
	 * @param cls
	 * @return
	 */
	private List setFails(List<Map>cls){
		List<Map>stds;		
		//List<Map>tmp=new ArrayList();
		List<Map>tmp1=new ArrayList();
		List<Map>tmp2=new ArrayList();
		List<Map>tmp3=new ArrayList();
		List<Map>tmp4=new ArrayList();
		List<Map>tmp5=new ArrayList();//90分
		
		Map m;
		Map f1=new HashMap();
		Map f2=new HashMap();
		Map f3=new HashMap();
		Map f4=new HashMap();
		Map f5=new HashMap();
		
		f1.put("cname", "各班導師");f1.put("cnt", "999");f1.put("ClassNo", "操行丁等學生");f1.put("ClassName", "操行丁等學生");		
		f2.put("cname", "各班導師");f2.put("cnt", "999");f2.put("ClassNo", "曠課45節");f2.put("ClassName", "曠課45節學生");
		f5.put("cname", "各班導師");f5.put("cnt", "999");f5.put("ClassNo", "90分以上");f5.put("ClassName", "操行成績90以上學生");
		
		int rank[];
		int score;
		StringBuilder sb;
		//丁等95分和45節
		for(int i=0; i<cls.size(); i++){
			stds=(List)cls.get(i).get("stds");
			rank=new int[7];
			sb=new StringBuilder();
			
			for(int j=0; j<stds.size(); j++){	
				
				if(!stds.get(j).get("down_year").equals("")){rank[6]=rank[6]+1;}
				
				score=Integer.parseInt(stds.get(j).get("total_score").toString());
				
				//丁等
				if(score<60){
					m=new HashMap();
					m.putAll(stds.get(j));							
					tmp1.add(m);
					tmp1.get(tmp1.size()-1).put("student_no", cls.get(i).get("ClassName").toString()+m.get("student_no"));
					rank[5]=rank[5]+1;
				}
				
				if(score>=60&&score<70){rank[4]=rank[4]+1;};
				if(score>=70&&score<80){rank[3]=rank[3]+1;};
				if(score>=80&&score<90){rank[2]=rank[2]+1;};
				if(score>=90&&score<95){rank[1]=rank[1]+1;};
				if(score>=95){rank[0]=rank[0]+1;};				
				
				//45節
				if(stds.get(j).get("dilg45")!=null){
					m=new HashMap();
					m.putAll(stds.get(j));							
					tmp2.add(m);
					tmp2.get(tmp2.size()-1).put("student_no", cls.get(i).get("ClassName").toString()+m.get("student_no"));
				}
				
				//90分
				if(score>=90){
					m=new HashMap();
					m.putAll(stds.get(j));							
					tmp5.add(m);
					tmp5.get(tmp5.size()-1).put("student_no", cls.get(i).get("ClassName").toString()+m.get("student_no"));
				}
			}
			
			if(rank[0]>0)sb.append("特優:"+rank[0]+", ");
			if(rank[1]>0)sb.append("優:"+rank[1]+", ");
			if(rank[2]>0)sb.append("甲:"+rank[2]+", ");
			if(rank[3]>0)sb.append("乙:"+rank[3]+", ");
			if(rank[4]>0)sb.append("丙:"+rank[4]+", ");
			if(rank[5]>0)sb.append("丁:"+rank[5]+", ");
			if(rank[6]>0)sb.append("定查:"+rank[6]+"  ");
			sb.delete(sb.length()-2, sb.length());
			cls.get(i).put("rankCount", rank);
			cls.get(i).put("rank", sb);
		}
		
		//畢業結算
		if(grade.equals("1")){
			f3.put("cname", "各班導師");f3.put("cnt", "999");f3.put("ClassNo", "歷年全勤");f3.put("ClassName", "歷年全勤學生");			
			tmp=df.sqlGet("SELECT c.student_no, cl.ClassName, s.student_name, ROUND(AVG(c.score),1)as total_score," +
			"COUNT(if(c.noabsent='N', true, null)) as absent FROM cond c, stmd s, Class cl WHERE " +
			"cl.ClassNo=s.depart_class AND cl.CampusNo='"+cno+"' AND cl.SchoolType='"+tno+"' AND cl.graduate='1' AND " +
			"c.student_no=s.student_no GROUP BY c.student_no");			
			for(int i=0; i<cls.size(); i++){
				stds=(List<Map>)cls.get(i).get("stds");
				for(int j=0; j<stds.size(); j++){
					for(int k=0; k<tmp.size(); k++){
						if(tmp.get(k).get("absent").toString().equals("0")){
							if(tmp.get(k).get("student_no").equals(stds.get(j).get("student_no"))){	
								m=new HashMap();
								m.putAll(stds.get(j));							
								tmp3.add(m);								
								tmp3.get(tmp3.size()-1).put("student_no", cls.get(i).get("ClassName").toString()+m.get("student_no"));
							}
						}						
					}
				}
			}			
			f4.put("cname", "各班導師");f4.put("cnt", "999");f4.put("ClassNo", "歷年前五名");f4.put("ClassName", "歷年前五名");			
			int si;
			for(int i=0; i<cls.size(); i++){
				tmp=df.sqlGet("SELECT IFNULL(ROUND(AVG(c.score),1),0)as score, s.student_no FROM cond c, stmd s WHERE (c.school_year!='"+getContext().getAttribute("school_year")+"' & c.school_term!='"+getContext().getAttribute("school_term")+"') AND " +
				"c.student_no=s.student_no AND s.depart_class='"+cls.get(i).get("ClassNo")+"' GROUP BY c.student_no");				
				tmp=bm.sortListByKey(tmp, "score", true);				
				stds=(List<Map>)cls.get(i).get("stds");
				
				if(tmp.size()<5){
					si=tmp.size();
				}else{
					si=5;
				}				
				
				for(int j=0; j<si; j++){					
					for(int k=0; k<stds.size(); k++){						
						if(tmp.get(j).get("student_no").equals(stds.get(k).get("student_no"))){							
							m=new HashMap();
							m.putAll(stds.get(k));							
							tmp4.add(m);
							tmp4.get(tmp4.size()-1).put("student_no", cls.get(i).get("ClassName").toString()+m.get("student_no"));
							tmp4.get(tmp4.size()-1).put("com_code1", "歷年平均:"+tmp.get(j).get("score"));
							tmp4.get(tmp4.size()-1).put("com_code2", "");
							tmp4.get(tmp4.size()-1).put("com_code3", "");							
						}
					}					
				}				
			}			
		}
		
		f1.put("stds", tmp1);
		cls.add(0, f1);		
		f2.put("stds", tmp2);
		cls.add(0, f2);		
		f3.put("stds", tmp3);
		cls.add(0, f3);		
		f4.put("stds", tmp4);
		cls.add(0, f4);
		
		f5.put("stds", bm.sortListByKey(tmp5, "total_score", false));
		cls.add(0, f5);
		
		tmp=null;tmp1=null;tmp2=null;tmp3=null;tmp4=null;tmp5=null;
		return cls;
	}	
}