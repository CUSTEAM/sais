package action.desd;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Message;
import action.BaseAction;
import action.desd.desdManager.DesdPrint;

public class DesdManager extends BaseAction{
	
	public String cno;
	public String sno;
	public String dno;
	public String gno;
	public String zno;
	
	
	public String cause;
	
	public String Oid;
	//public String depart_class;
	public String stdNo;
	public String ddate;
	public String no;
	public String reason;
	public String kind1;
	public String cnt1;
	public String kind2;
	public String cnt2;	
	public String act_illegal;
	
	public String nameno;
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");	
	public String execute(){		
		return SUCCESS;
	}
	
	/**
	 * 建立
	 * @return
	 */
	public String add(){
		if(ddate.trim().equals("")||no.trim().equals("")||reason.trim().equals("")){
			Message msg=new Message();
			msg.setError("請檢查日期、文號、原因");
			savMessage(msg);
			return SUCCESS;
		}
		if( (kind1.equals("")&&kind2.equals("")&&cnt1.equals("")&&cnt2.equals(""))||
				((kind1.equals("")&&!cnt1.equals("")) || (!kind1.equals("")&&cnt1.equals("")))|| 
				((kind2.equals("")&&!cnt2.equals("")) || (!kind2.equals("")&&cnt2.equals("")))){
			Message msg=new Message();
			msg.setError("請檢查種類與次數");
			savMessage(msg);
			return SUCCESS;
		}		
		
		saveDesd(stdNo);
		return search();
	}
	
	private void saveDesd(String stdNo){		
		int s1, s2;
		try{
			s1=df.sqlGetInt("SELECT deduct FROM CODE_DESD WHERE id='"+kind1+"'")*Integer.parseInt(cnt1);
		}catch(Exception e){
			s1=0;
		}
		
		try{
			s2=df.sqlGetInt("SELECT deduct FROM CODE_DESD WHERE id='"+kind2+"'")*Integer.parseInt(cnt2);
		}catch(Exception e){
			s2=0;
		}
		
		StringBuilder sb=new StringBuilder("INSERT INTO desd(student_no,ddate,no,reason,kind1,cnt1,kind2,cnt2,score,act_illegal)VALUES" +
		"('"+stdNo+"','"+ddate+"','"+no+"','"+reason+"',");		
		if(kind1.equals("")){sb.append("null,");}else{sb.append("'"+kind1+"',");}
		if(cnt1.equals("")){sb.append("null,");}else{sb.append("'"+cnt1+"',");}
		if(kind2.equals("")){sb.append("null,");}else{sb.append("'"+kind2+"',");}
		if(cnt2.equals("")){sb.append("null,");}else{sb.append("'"+cnt2+"',");}
		sb.append((s1+s2)+",");
		sb.append("'"+act_illegal+"');");
		
		df.exSql(sb.toString());
		
	}
	
	public String addAll(){		
		
		Message msg=new Message();
		if(ddate.trim().equals("")||no.trim().equals("")||reason.trim().equals("")){			
			msg.setError("請檢查日期、文號、原因");
			savMessage(msg);
			return SUCCESS;
		}		
		StringBuilder sql=new StringBuilder("SELECT s.student_no FROM Class c, stmd s WHERE s.depart_class=c.ClassNo AND c.CampusNo='"+cno+"'");
		if(!sno.equals(""))sql.append("AND SchoolNo='"+sno+"'");
		if(!dno.equals(""))sql.append("AND DeptNo='"+dno+"'");
		if(!gno.equals(""))sql.append("AND Grade='"+gno+"'");
		if(!zno.equals(""))sql.append("AND SeqNo='"+zno+"'");		
		
		List<Map>stds=df.sqlGet(sql.toString());		
		
		for(int i=0; i<stds.size(); i++){
			try{
				saveDesd(stds.get(i).get("student_no").toString());
			}catch(Exception e){
				msg.addError(stds.get(i).get("student_no")+"建立失敗<br>");
			}			
		}
		
		
		request.setAttribute("stds", df.sqlGet("SELECT cdc.name, st.student_name,c.ClassName, d.* FROM " +
				"desd d LEFT OUTER JOIN CODE_DESD_CAUSE cdc ON d.reason=cdc.id, stmd st, Class c WHERE " +
				"st.student_no=d.student_no AND c.ClassNo=st.depart_class AND d.no='"+no+"'"));
		this.savMessage(msg);
		return SUCCESS;
	}
	
	/**
	 * 刪除
	 * @return
	 */
	public String del(){		
		df.exSql("DELETE FROM desd WHERE Oid="+Oid);		
		return search();
	}
	
	/**
	 * 查詢
	 * @return
	 */
	public String search(){
		
		if(stdNo.trim().equals("")){
			Message msg=new Message();
			msg.setError("請輸入學號");
			savMessage(msg);
			return SUCCESS;
		}
		List stds=df.sqlGet("SELECT cdc.name, st.student_name,c.ClassName, d.* FROM " +
				"desd d LEFT OUTER JOIN CODE_DESD_CAUSE cdc ON d.reason=cdc.id, stmd st, Class c WHERE " +
				"st.student_no=d.student_no AND c.ClassNo=st.depart_class AND d.student_no='"+stdNo+"'");
		
		if(stds.size()<1){
			Message msg=new Message();
			msg.setInfo("無資料");
			savMessage(msg);
			return SUCCESS;
		}
		request.setAttribute("stds", stds);
		return SUCCESS;
	}
	
	public String print() throws IOException{
		Map info=new HashMap();
		info.put("school_year", getContext().getAttribute("school_year"));
		info.put("school_term", getContext().getAttribute("school_term"));
		
		List<Map>desd=df.sqlGet("SELECT c.ClassNo, c.ClassName," +
		"(SELECT COUNT(DISTINCT s1.student_no) FROM desd d1, stmd s1 WHERE d1.student_no=s1.student_no AND s1.depart_class=c.ClassNo AND d1.kind1<=3)as cnt1, " +
		"(SELECT COUNT(DISTINCT s1.student_no) FROM desd d1, stmd s1 WHERE d1.student_no=s1.student_no AND s1.depart_class=c.ClassNo AND d1.kind1>=4)as cnt2, " +
		"SUM(IF(d.kind1='1', d.cnt1, 0))as kind11,SUM(IF(d.kind1='2', d.cnt1, 0))as kind12," +
		"SUM(IF(d.kind1='3', d.cnt1, 0))as kind13,SUM(IF(d.kind1='4', d.cnt1, 0))as kind14,SUM(IF(d.kind1='5', d.cnt1, 0))as kind15," +
		"SUM(IF(d.kind1='6', d.cnt1, 0))as kind16,SUM(IF(d.kind2='1', d.cnt2, 0))as kind21,SUM(IF(d.kind2='2', d.cnt2, 0))as kind22," +
		"SUM(IF(d.kind2='3', d.cnt2, 0))as kind23,SUM(IF(d.kind2='4', d.cnt2, 0))as kind24,SUM(IF(d.kind2='5', d.cnt2, 0))as kind25," +
		"SUM(IF(d.kind2='6', d.cnt2, 0))as kind26 FROM desd d, stmd s, Class c WHERE d.student_no=s.student_no AND s.depart_class=c.ClassNo " +
		"AND c.CampusNo='"+cno+"'AND c.SchoolNo LIKE '"+sno+"%' AND c.DeptNo LIKE'"+dno+"%' " +
		"AND c.Grade LIKE'"+gno+"%' AND c.SeqNo LIKE'"+zno+"%' GROUP BY c.ClassNo ORDER BY c.ClassNo");
		
		List<Map>desd2=df.sqlGet("SELECT c.ClassNo, c.ClassName," +
		"(SELECT COUNT(DISTINCT s1.student_no) FROM desd d1, stmd s1 WHERE d1.student_no=s1.student_no AND s1.depart_class=c.ClassNo AND d1.kind1<=3 AND s1.sex='1')as cnt1, " +
		"(SELECT COUNT(DISTINCT s1.student_no) FROM desd d1, stmd s1 WHERE d1.student_no=s1.student_no AND s1.depart_class=c.ClassNo AND d1.kind1>=4 AND s1.sex='1')as cnt2, " +
		"SUM(IF(d.kind1='1', d.cnt1, 0))as kind11,SUM(IF(d.kind1='2', d.cnt1, 0))as kind12," +
		"SUM(IF(d.kind1='3', d.cnt1, 0))as kind13,SUM(IF(d.kind1='4', d.cnt1, 0))as kind14,SUM(IF(d.kind1='5', d.cnt1, 0))as kind15," +
		"SUM(IF(d.kind1='6', d.cnt1, 0))as kind16,SUM(IF(d.kind2='1', d.cnt2, 0))as kind21,SUM(IF(d.kind2='2', d.cnt2, 0))as kind22," +
		"SUM(IF(d.kind2='3', d.cnt2, 0))as kind23,SUM(IF(d.kind2='4', d.cnt2, 0))as kind24,SUM(IF(d.kind2='5', d.cnt2, 0))as kind25," +
		"SUM(IF(d.kind2='6', d.cnt2, 0))as kind26 FROM desd d, stmd s, Class c WHERE d.student_no=s.student_no AND s.depart_class=c.ClassNo AND s.sex='1'" +
		"AND c.CampusNo='"+cno+"'AND c.SchoolNo LIKE '"+sno+"%' AND c.DeptNo LIKE'"+dno+"%' " +
		"AND c.Grade LIKE'"+gno+"%' AND c.SeqNo LIKE'"+zno+"%' GROUP BY c.ClassNo ORDER BY c.ClassNo");
		
		List<Map>desd3=df.sqlGet("SELECT c.ClassNo, c.ClassName," +
		"(SELECT COUNT(DISTINCT s1.student_no) FROM desd d1, stmd s1 WHERE d1.student_no=s1.student_no AND s1.depart_class=c.ClassNo AND d1.kind1<=3 AND s1.sex='2')as cnt1, " +
		"(SELECT COUNT(DISTINCT s1.student_no) FROM desd d1, stmd s1 WHERE d1.student_no=s1.student_no AND s1.depart_class=c.ClassNo AND d1.kind1>=4 AND s1.sex='2')as cnt2, " +
		"SUM(IF(d.kind1='1', d.cnt1, 0))as kind11,SUM(IF(d.kind1='2', d.cnt1, 0))as kind12," +
		"SUM(IF(d.kind1='3', d.cnt1, 0))as kind13,SUM(IF(d.kind1='4', d.cnt1, 0))as kind14,SUM(IF(d.kind1='5', d.cnt1, 0))as kind15," +
		"SUM(IF(d.kind1='6', d.cnt1, 0))as kind16,SUM(IF(d.kind2='1', d.cnt2, 0))as kind21,SUM(IF(d.kind2='2', d.cnt2, 0))as kind22," +
		"SUM(IF(d.kind2='3', d.cnt2, 0))as kind23,SUM(IF(d.kind2='4', d.cnt2, 0))as kind24,SUM(IF(d.kind2='5', d.cnt2, 0))as kind25," +
		"SUM(IF(d.kind2='6', d.cnt2, 0))as kind26 FROM desd d, stmd s, Class c WHERE d.student_no=s.student_no AND s.depart_class=c.ClassNo AND s.sex='2' " +
		"AND c.CampusNo='"+cno+"'AND c.SchoolNo LIKE '"+sno+"%' AND c.DeptNo LIKE'"+dno+"%' " +
		"AND c.Grade LIKE'"+gno+"%' AND c.SeqNo LIKE'"+zno+"%' GROUP BY c.ClassNo ORDER BY c.ClassNo");
		
		DesdPrint p=new DesdPrint();
		p.print(response, df.sqlGet("SELECT st.sex, (SELECT name FROM CODE_DESD WHERE id=d.kind1)as k1name,(SELECT name FROM " +
		"CODE_DESD WHERE id=d.kind2)as k2name, cdc.name, st.student_name,c.ClassName, d.* FROM desd d LEFT OUTER JOIN CODE_DESD_CAUSE cdc ON " +
		"d.reason=cdc.id, stmd st, Class c WHERE st.student_no=d.student_no AND c.ClassNo=st.depart_class AND  c.CampusNo='"+cno+"' AND " +
		"c.SchoolNo LIKE'"+sno+"%'AND c.DeptNo LIKE'"+dno+"%' AND c.Grade LIKE'"+gno+"%' AND c.seqNo LIKE'"+zno+"%' ORDER BY c.ClassNo, " +
		"d.student_no, d.ddate"),desd, desd2, desd3, info);
		p=null;
		return null;
	}
}
