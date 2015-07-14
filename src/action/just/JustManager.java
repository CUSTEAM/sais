package action.just;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Dilg;
import model.DilgApply;
import model.Just;
import model.Message;
import action.BaseAction;
import action.rollCall.TimeOffManagerAction;

/**
 * 結算後修正管理
 * @author John
 *
 */
public class JustManager extends TimeOffManagerAction{
	
	public String cno;
	public String sno;
	public String dno;
	public String gno;
	public String zno;
	public String stdNo;
	public String nameno;
	public String editStd;
	
	public String Oid[];
	public String abs[];
	
	public int begin, end;
	public String beginDate, endDate, absType;
	
	public String execute(){		
		return SUCCESS;
	}
	
	/**
	 * 選取單一學生準備修改
	 * @return
	 */
	public String edit(){
		if(editStd==null){
			editStd=stdNo;
		}						
		List<Map>list=sam.getCsDilgDetail(editStd, "", "2012-12-21", "2035-12-21");
		request.setAttribute("dilgs", list);
		request.setAttribute("info", sam.StudentDilg(editStd));
		request.setAttribute("cs", sam.getDilgDetail(editStd, getContext().getAttribute("school_term").toString()));		
		request.setAttribute("just", df.sqlGetStr("SELECT total_score FROM Just WHERE student_no='"+editStd+"'"));//判斷該生是否已結算
		request.setAttribute("failSeld", sam.getFailStd(editStd));//已標記扣考
		request.setAttribute("dilgHist", sam.getDilgRecord(editStd));
		request.setAttribute("endStart", sam.getEndAtStart(editStd));
		return "edit";
	}
	
	private void reCount(){
		//重新計算Just,Seld
				List<Map>dilgRule=df.sqlGet("SELECT id, score FROM Dilg_rules WHERE score>0");//取得dilg rules
				//將所有seld之status設回null
				df.exSql("UPDATE Seld SET status=null WHERE student_no='"+stdNo+"'");		
				//重新標記seld status
				List<Map>list=df.sqlGet("SELECT d.student_no, d.Dtime_oid, ((dt.thour*18)/3)as thour FROM stmd s, Dilg d, Dtime dt, Class c " +
				"WHERE s.student_no='"+stdNo+"' AND s.depart_class=c.ClassNo AND s.student_no=d.student_no AND dt.Oid=d.Dtime_oid AND " +
				"d.abs IN(SELECT id FROM Dilg_rules WHERE exam='1')GROUP BY d.student_no, d.Dtime_oid HAVING COUNT(*)>=thour ");
				for(int i=0; i<list.size(); i++){
					df.exSql("UPDATE Seld SET status='1' WHERE student_no='"+list.get(i).get("student_no")+"' AND Dtime_oid="+list.get(i).get("Dtime_oid"));
				}
				
				//建立遠距扣考標記
				list=df.sqlGet("SELECT s.student_no, s.Dtime_oid, s.elearn_dilg,((d.thour*18)/3)as thour " +
				"FROM Seld s, stmd st, Dtime d WHERE s.elearn_dilg>6 AND st.student_no='"+stdNo+"'AND " +
				"d.Oid=s.Dtime_oid AND s.student_no=st.student_no HAVING s.elearn_dilg>=thour");		
				for(int i=0; i<list.size(); i++){
					df.exSql("UPDATE Seld SET status='1' WHERE student_no='"+stdNo+"' AND Dtime_oid="+list.get(i).get("Dtime_oid"));
				}
				
				//計算Just之dilg_score並改寫total_score
				float dilg=0f;
				int total_score=0;
				List<Map>dilgs=df.sqlGet("SELECT COUNT(*)as cnt, abs FROM Seld s LEFT OUTER JOIN Dilg d ON " +
				"s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid WHERE s.status IS NULL AND d.student_no='"+stdNo+"' GROUP BY d.abs");
				
				for(int k=0; k<dilgs.size(); k++){
					for(int l=0; l<dilgRule.size(); l++){					
						if(dilgs.get(k).get("abs").equals(dilgRule.get(l).get("id"))){						
							dilg=dilg+(Integer.parseInt(dilgs.get(k).get("cnt").toString())*Float.parseFloat(dilgRule.get(l).get("score").toString()));						
						}
					}
				}
				//寫入新dilg_score
				df.exSql("UPDATE Just SET dilg_score="+(0-Math.round(dilg))+" WHERE student_no='"+stdNo+"'");
				//重新計算total_score
				int init=85;
				if(dilg>0){
					init=82;
				}
				df.exSql("UPDATE Just SET total_score="+init+"+(teacher_score+deptheader_score+military_score+dilg_score+desd_score+meeting_score)WHERE student_no='"+stdNo+"'");
		
	}
	
	
	/**
	 * 修改缺曠
	 * @return
	 */
	public String saveDilg(){		
		for(int i=0; i<Oid.length; i++){			
			if(!Oid[i].equals("")){
				//刪除缺課
				if(abs[i].equals("")){//假單保留
					sam.saveRecord(Oid[i], getSession().getAttribute("userid").toString(), "刪除");//留底
					df.exSql("DELETE FROM Dilg WHERE Oid="+Oid[i]);//刪缺曠					
				}
				
				//改假別非空白、非2、非5
				if(!abs[i].equals("")&&!abs[i].equals("2")&&!abs[i].equals("5")){
					//更改內容
					sam.saveRecord(Oid[i], getSession().getAttribute("userid").toString(), "修改假別為"+abs[i]);//留底
					Dilg d=(Dilg) df.hqlGetListBy("FROM Dilg WHERE Oid="+Oid[i]).get(0);
					d.setAbs(abs[i]);
					DilgApply da;
					if(d.getDilg_app_oid()!=null){
						da=(DilgApply) df.hqlGetListBy("FROM DilgApply WHERE Oid="+d.getDilg_app_oid()).get(0);						
					}else{
						//fuc!1張1節
						da=new DilgApply();
						da.setAbs(abs[i]);
						da.setAuditor((String) getSession().getAttribute("userid"));
						da.setDefaultLevel("1");
						da.setRealLevel("1");
						da.setResult("1");
						da.setCr_date(new Date());
						da.setStudent_no(d.getStudent_no());
						df.update(da);
					}
					d.setDilg_app_oid(da.getOid());
					df.update(d);					
				}				
				
				//請假改曠課
				if(abs[i].equals("2")){
					sam.saveRecord(Oid[i], getSession().getAttribute("userid").toString(), "修改為曠課");//留底
					df.exSql("UPDATE Dilg SET abs='2', Dilg_app_oid=null WHERE Oid="+Oid[i]);
				}
				//改為遲到
				if(abs[i].equals("5")){
					sam.saveRecord(Oid[i], getSession().getAttribute("userid").toString(), "修改為遲到");//留底
					df.exSql("UPDATE Dilg SET abs='5', Dilg_app_oid=null WHERE Oid="+Oid[i]);
				}
			}
		}
		
		reCount();
		
		return edit();
	}
	
	/**
	 * 新增缺曠
	 * @return
	 * @throws ParseException 
	 */
	public String addDilg() throws ParseException{
		
		if(begin>end){
			Message m=new Message();
			m.setError("節次矛盾");
			savMessage(m);
			return execute();
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar b=Calendar.getInstance();
		Calendar e=Calendar.getInstance();
		b.setTime(sf.parse(beginDate));
		e.setTime(sf.parse(endDate));
		
		if(b.getTimeInMillis()>e.getTimeInMillis()){
			Message m=new Message();
			m.setError("日期矛盾");
			savMessage(m);
			return execute();
		}
		
		int days=(int)((e.getTimeInMillis()-b.getTimeInMillis())/(1000*3600*24));//天
		
		//建立假單
		DilgApply da=null;
		if(!absType.equals("2")&&!absType.equals("5")){
			da=new DilgApply();//非遲到且非曠課時才建立假單
			da.setAbs(absType);		
			//da.setReason(reason);		
			da.setAuditor((String) getSession().getAttribute("userid"));		
			da.setStudent_no(stdNo);
			da.setRealLevel("1");
			da.setResult("1");
			da.setDefaultLevel("1");
			da.setCr_date(new Date());
			df.update(da);
		}
		
		List <Map>dClass;
		boolean checkEmpty=false;
		for(int i=0; i<=days; i++){
			dClass=df.sqlGet("SELECT dc.* FROM Dtime d,Seld s,Dtime_class dc WHERE d.Sterm='"+getContext().getAttribute("school_term")+"'AND " +
			"d.Oid=s.Dtime_oid AND d.Oid=dc.Dtime_oid AND dc.week='"+(b.get(Calendar.DAY_OF_WEEK)-1)+"'AND((dc.begin>="+begin+" AND dc.begin<="+end+")OR " +
			"(dc.end>="+begin+" AND dc.end<="+end+")) AND s.student_no='"+stdNo+"'");			
			
			if(dClass.size()>0){
				checkEmpty=true;
			}
			//建立缺曠
			for(int j=0; j<dClass.size(); j++){
				if(da!=null){
					saveDilg(stdNo, dClass.get(j), b.getTime(), da.getOid(), absType);
				}else{
					saveDilg(stdNo, dClass.get(j), b.getTime(), null, absType);
				}								
			}			
			b.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		if(!checkEmpty){
			Message m=new Message();
			m.setError(beginDate+" 第"+begin+"節至<br>"+endDate+" 第"+end+"節<br>學生無選課記錄");
			if(da!=null)
			df.exSql("DELETE FROM Dilg_apply WHERE Oid="+da.getOid());
			da=null;
			savMessage(m);
		}	
		reCount();
		return edit();
	}
	
	
	/**
	 * 搜尋列表
	 * @return
	 */
	public String search(){
		List <Map>stds;
		//個人或班級,結算後的學生
		if(stdNo.trim().equals("")){
			stds=df.sqlGet("SELECT s.student_no, s.student_name, j.teacher_score, j.deptheader_score, j.military_score, j.meeting_score, j.dilg_score, j.desd_score,j.total_score," +
			"com_code1, com_code2, com_code3 FROM stmd s LEFT OUTER JOIN Just j ON j.student_no=s.student_no, " +
			"Class c WHERE c.ClassNo=s.depart_class AND c.CampusNo='"+cno+"' AND c.SchoolNo='"+sno+"' AND c.DeptNo='"+dno+"' AND " +
			"c.Grade='"+gno+"' AND c.SeqNo='"+zno+"'AND j.total_score IS NOT NULL");
		}
		
		else{
			stds=df.sqlGet("SELECT s.student_no, s.student_name, j.teacher_score, j.deptheader_score, j.military_score, j.meeting_score, j.dilg_score," +
			"j.desd_score,j.total_score,com_code1, com_code2, com_code3 FROM stmd s LEFT OUTER JOIN Just j ON j.student_no=s.student_no " +
			"WHERE s.student_no='"+stdNo+"'AND j.total_score IS NOT NULL");
		}
		
		if(stds.size()<1){
			Message msg=new Message();
			msg.setError("尚未結算無需修正");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		for(int i=0; i<stds.size(); i++){
			chStd(stds.get(i));
		}
		
		request.setAttribute("stds", stds);	
		return SUCCESS;
	}
	
	/**
	 * 批次儲存
	 * @return
	 */
	public String save(){
		
		Just j;
		for(int i=0; i<student_no.length; i++){	
			System.out.println(student_no[i]);
			j=new Just();
			j.setStudentNo(student_no[i]);
			//if(com_code1[i].indexOf(",")>0){j.setComCode1(com_code1[i].substring(0, com_code1[i].indexOf(",")));}
			//if(com_code2[i].indexOf(",")>0){j.setComCode2(com_code2[i].substring(0, com_code2[i].indexOf(",")));}
			//if(com_code3[i].indexOf(",")>0){j.setComCode3(com_code3[i].substring(0, com_code3[i].indexOf(",")));}
			if(com_code1[i].trim().length()>0)j.setComCode1(com_code1[i]);
			if(com_code2[i].trim().length()>0)j.setComCode2(com_code2[i]);
			if(com_code3[i].trim().length()>0)j.setComCode3(com_code3[i]);
			if(!desd_score[i].trim().equals("")){j.setDesdScore(Integer.parseInt(desd_score[i]));}
			if(!teacher_score[i].trim().equals("")){j.setTeacherScore(Integer.parseInt(teacher_score[i]));}
			if(!deptheader_score[i].trim().equals("")){j.setDeptheaderScore(Integer.parseInt(deptheader_score[i]));}
			if(!military_score[i].trim().equals("")){j.setMilitaryScore(Integer.parseInt(military_score[i]));}
			if(!meeting_score[i].trim().equals("")){j.setMeetingScore(Integer.parseInt(meeting_score[i]));}
			if(!dilg_score[i].trim().equals("")){j.setDilgScore(Integer.parseInt(dilg_score[i]));}
			if(!total_score[i].trim().equals("")){j.setTotalScore(Integer.parseInt(total_score[i]));}
			df.update(j);
		}
		
		return search();
	}
	
	/**
	 * 非0 null
	 * @param m
	 * @return
	 */
	private Map chStd(Map m){		
		if(m.get("teacher_score")==null){m.put("teacher_score", 0);}
		if(m.get("deptheader_score")==null){m.put("deptheader_score", 0);}
		if(m.get("military_score")==null){m.put("military_score", 0);}
		if(m.get("meeting_score")==null){m.put("meeting_score", 0);}
		if(m.get("desd_score")==null){m.put("desd_score", 0);}
		if(m.get("dilg_score")==null){m.put("dilg_score", "0");}
		return m;
	}
	
	public String[] com_code1;
	public String[] com_code2;
	public String[] com_code3;	
	public String[] student_no;
	public String[] teacher_score;
	public String[] deptheader_score;
	public String[] military_score;	
	public String[] dilg_score;
	public String[] desd_score;
	public String[] meeting_score;
	public String[] total_score;
}