package action.tutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Message;
import action.BaseAction;

/**
 * 操行加權、評語
 * @author John
 *
 */
public class ConductCreditsAction extends BaseAction{
	
	public String execute(){		
		//導師、系主任、教官班級
		List <Map>list=df.sqlGet("SELECT (SELECT COUNT(*)FROM stmd WHERE depart_class=ClassNo)as cnt, " +
		"(SELECT COUNT(*)FROM Just, stmd WHERE stmd.student_no=Just.student_no AND stmd.depart_class=ClassNo AND Just.total_score IS NOT NULL)as score, ClassName, ClassNo, 'T' as rule FROM " +
		"Class WHERE tutor='"+getSession().getAttribute("userid")+"' ORDER BY ClassNo");//導師權限	
		
		
		//系主任
		list.addAll(df.sqlGet("SELECT (SELECT COUNT(*)FROM stmd WHERE depart_class=c.ClassNo)as cnt, " +
		"(SELECT COUNT(*)FROM Just, stmd WHERE stmd.student_no=Just.student_no AND stmd.depart_class=ClassNo AND Just.total_score IS NOT NULL)as score, c.ClassName, c.ClassNo, 'D' as rule FROM " +
		"CODE_DEPT d, Class c WHERE (c.Type='P' OR c.Type='E') AND d.id=c.DeptNo AND d.director='"+getSession().getAttribute("userid")+"' ORDER BY c.ClassNo"));
		
		//日教官
		list.addAll(df.sqlGet("SELECT (SELECT COUNT(*)FROM stmd WHERE depart_class=c.ClassNo)as cnt, " +
		"(SELECT COUNT(*)FROM Just, stmd WHERE stmd.student_no=Just.student_no AND stmd.depart_class=ClassNo AND Just.total_score IS NOT NULL)as score, c.ClassName, c.ClassNo, 'M' as rule FROM " +
		"CODE_DEPT d, Class c WHERE c.SchoolType='D' AND (c.Type='P' OR c.Type='E') AND d.id=c.DeptNo AND d.military='"+getSession().getAttribute("userid")+"' ORDER BY c.ClassNo"));
		
		//夜教官
		list.addAll(df.sqlGet("SELECT (SELECT COUNT(*)FROM stmd WHERE depart_class=c.ClassNo)as cnt, " +
		"(SELECT COUNT(*)FROM Just, stmd WHERE stmd.student_no=Just.student_no AND stmd.depart_class=ClassNo AND Just.total_score IS NOT NULL)as score, c.ClassName, c.ClassNo, 'M' as rule FROM " +
		"CODE_DEPT d, Class c WHERE c.SchoolType='N' AND (c.Type='P' OR c.Type='E') AND d.id=c.DeptNo AND d.military_n='"+getSession().getAttribute("userid")+"' ORDER BY c.ClassNo"));
		//補教官
		list.addAll(df.sqlGet("SELECT (SELECT COUNT(*)FROM stmd WHERE depart_class=c.ClassNo)as cnt, " +
		"(SELECT COUNT(*)FROM Just, stmd WHERE stmd.student_no=Just.student_no AND stmd.depart_class=ClassNo AND Just.total_score IS NOT NULL)as score, c.ClassName, c.ClassNo, 'M' as rule FROM " +
		"CODE_DEPT d, Class c WHERE c.SchoolType='N' AND (c.Type='P' OR c.Type='E') AND d.id=c.DeptNo AND d.military_h='"+getSession().getAttribute("userid")+"' ORDER BY c.ClassNo"));
		
		request.setAttribute("myClass", list);
		
		return SUCCESS;
	}
	
	private List getStds(){		
		
		//本班扣考
		List<Map>fails=df.sqlGet("SELECT d.student_no, d.Dtime_oid, ((dt.thour*18)/3)as thour FROM stmd s, Dilg d, Dtime dt, Class c WHERE " +
		"s.depart_class=c.ClassNo AND c.ClassNo='"+ClassNo+"' AND s.student_no=d.student_no AND dt.Oid=d.Dtime_oid AND d.abs IN(SELECT id FROM Dilg_rules WHERE exam='1')" +
		"GROUP BY d.student_no, d.Dtime_oid HAVING COUNT(*)>=thour");
		List<Map>dilgs=new ArrayList();
		for(int i=0; i<fails.size(); i++){
			dilgs.addAll(df.sqlGet("SELECT Oid FROM Dilg WHERE student_no='"+fails.get(i).get("student_no")+"' AND Dtime_oid="+fails.get(i).get("Dtime_oid")));
		}
		StringBuilder sb=new StringBuilder();
		if(fails.size()>0){
			for(int i=0; i<dilgs.size(); i++){
				sb.append("'"+dilgs.get(i).get("Oid")+"',");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		
		//全部學生
		List <Map>stds=df.sqlGet("SELECT s.student_no, com_code1, com_code2, com_code3, " +
		"s.student_name, j.teacher_score, j.deptheader_score, j.military_score, j.meeting_score, com_code1, com_code2, com_code3 FROM stmd s LEFT OUTER JOIN Just j ON j.student_no=s.student_no WHERE s.depart_class='"+ClassNo+"' ORDER BY s.student_no");		
				
		if(stds.get(0).get("total_score")==null){
			List <Map>dilgRule=df.sqlGet("SELECT id, score FROM Dilg_rules WHERE score>0");//扣分標準
			List <Map>desdRule=df.sqlGet("SELECT id FROM CODE_DESD");
			Float dilg;
			int desd;			
			for(int i=0; i<stds.size(); i++){				
				desd=0;
				dilg=0.0f;
				
				//缺曠
				if(sb.length()>0){//有扣考
					for(int j=0; j<dilgRule.size(); j++){					
						dilg=dilg+df.sqlGetInt("SELECT COUNT(*)FROM Dilg WHERE Oid NOT IN("+sb+")AND " +
						"student_no='"+stds.get(i).get("student_no")+"' AND " +
						"abs='"+dilgRule.get(j).get("id")+"'")*Float.parseFloat(dilgRule.get(j).get("score").toString());
					}
				}else{//無扣考
					for(int j=0; j<dilgRule.size(); j++){					
						dilg=dilg+df.sqlGetInt("SELECT COUNT(*)FROM Dilg WHERE " +
						"student_no='"+stds.get(i).get("student_no")+"' AND " +
						"abs='"+dilgRule.get(j).get("id")+"'")*Float.parseFloat(dilgRule.get(j).get("score").toString());
					}
				}
				
				//獎懲
				for(int j=0; j<desdRule.size(); j++){					
					try{
						desd=desd+df.sqlGetInt("SELECT (SUM(d.cnt1)*c.deduct) FROM desd d, CODE_DESD c WHERE " +
						"c.id=d.kind1 AND d.kind1='"+desdRule.get(j).get("id")+"' AND d.student_no='"+stds.get(i).get("student_no")+"' GROUP BY d.kind1");
					}catch(Exception e){}
					
					try{
						desd=desd+df.sqlGetInt("SELECT (SUM(d.cnt2)*c.deduct) FROM desd d, CODE_DESD c WHERE " +
						"c.id=d.kind2 AND d.kind2='"+desdRule.get(j).get("id")+"' AND d.student_no='"+stds.get(i).get("student_no")+"' GROUP BY d.kind2");
					}catch(Exception e){}
				}				
				if(stds.get(i).get("teacher_score")==null){stds.get(i).put("teacher_score", 0);}
				if(stds.get(i).get("deptheader_score")==null){stds.get(i).put("deptheader_score", 0);}
				if(stds.get(i).get("military_score")==null){stds.get(i).put("military_score", 0);}
				if(stds.get(i).get("meeting_score")==null){stds.get(i).put("meeting_score", 0);}				
				stds.get(i).put("dilg_score", Math.round(dilg));
				stds.get(i).put("desd_score", desd);				
			}			
		}else{			
			stds=df.sqlGet("SELECT (SELECT name FROM code1 WHERE no=j.com_code1)as com_name1," +
			"(SELECT name FROM code1 WHERE no=j.com_code2)as com_name2, (SELECT name FROM code1 WHERE no=j.com_code3)as " +
			"com_name3,s.student_name, j.* FROM stmd s, Just j WHERE s.student_no=j.student_no AND s.depart_class='"+ClassNo+"'");			
		}
		return stds;
	}
	
	
	public String edit(){		
		request.setAttribute("stds", getStds());
		request.setAttribute("codeInfo", df.sqlGet("SELECT no, name FROM code1"));
		return "edit";
	}
	
	public String view(){
		request.setAttribute("view", true);
		request.setAttribute("stds", getStds());		
		return "edit";
	}
	
	public String save(){
		for(int i=0; i<student_no.length; i++){
			try{
				
				com_code1[i]=df.sqlGetStr("SELECT name FROM code1 WHERE no='"+com_code1[i]+"'");
			}catch(Exception e){}
			
			try{
				com_code2[i]=df.sqlGetStr("SELECT name FROM code1 WHERE no='"+com_code2[i]+"'");
			}catch(Exception e){}
			
			try{
				com_code3[i]=df.sqlGetStr("SELECT name FROM code1 WHERE no='"+com_code3[i]+"'");
			}catch(Exception e){}
			
			
			try{
				df.exSql("INSERT INTO Just(student_no,teacher_score,deptheader_score,military_score,com_code1,com_code2,com_code3)VALUES" +
				"('"+student_no[i]+"', "+teacher_score[i]+" ,"+deptheader_score[i]+", "+military_score[i]+","+com_code1[i]+","+com_code2[i]+","+com_code3[i]+");");
			}catch(Exception e){
				df.exSql("UPDATE Just SET com_code1='"+com_code1[i]+"', com_code2='"+com_code2[i]+"', com_code3='"+com_code3[i]+"', teacher_score="+teacher_score[i]+", " +
				"deptheader_score="+deptheader_score[i]+", military_score="+military_score[i]+" WHERE student_no='"+student_no[i]+"'");
			}
			
			
			
		}
		
		setClassNo(df.sqlGetStr("SELECT depart_class FROM stmd WHERE student_no='"+student_no[0]+"'"));
		
		Message msg=new Message();
		msg.setInfo("已儲存");
		this.savMessage(msg);
		
		return edit();
	}
	
	public String getClassNo() {
		return ClassNo;
	}


	public void setClassNo(String classNo) {
		ClassNo = classNo;
	}

	public String ClassNo;
	public String rule;
	
	public String[] com_code1;
	public String[] com_code2;
	public String[] com_code3;
	
	
	public String[] student_no;
	public String[] teacher_score;
	public String[] deptheader_score;
	public String[] military_score;

}
