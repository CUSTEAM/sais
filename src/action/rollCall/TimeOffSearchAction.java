package action.rollCall;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import action.BaseAction;
import action.rollCall.timeOffSearch.ListCls;
import action.rollCall.timeOffSearch.ListMail;
import action.rollCall.timeOffSearch.ListStd;
import model.Message;

public class TimeOffSearchAction extends BaseAction{
	
	public String cno;
	public String sno;
	public String dno;
	public String gno;
	public String zno;
	
	public String abs;
	public String less;
	public String more;
	
	public String beginDate;
	public String endDate;
	
	public String execute(){
		
		return SUCCESS;
	}
	
	
	/**
	 * 班級統計表
	 * @return
	 * @throws IOException
	 */
	public String listCls() throws IOException{		
		
		/*簡短但效率不佳
		List a=df.sqlGet("SELECT sum(abs='2') abs2, sum(abs='3') abs3, sum(abs='4') abs4, " +
				"sum(abs='6') abs6,(SELECT COUNT(*)FROM stmd WHERE stmd.student_no NOT IN " +
				"(SELECT DISTINCT Dilg.student_no FROM Dilg))as noabs FROM " +
				"Dilg d, stmd s WHERE d.date>='"+beginDate+"' AND d.date<='"+endDate+"' AND " +
				"d.student_no=s.student_no AND s.depart_class LIKE '"+cno+sno+dno+gno+zno+"%'");
		*/
		
		StringBuilder sb=new StringBuilder("SELECT ClassName,(SELECT COUNT(*)FROM stmd WHERE "
		+ "depart_class=Class.ClassNo)as sts, (SELECT COUNT(*)FROM Dilg,stmd WHERE "
		+ "Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='2')as abs2, (SELECT COUNT(*)FROM Dilg, stmd "
		+ "WHERE Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='3')as abs3,(SELECT COUNT(*)FROM Dilg, stmd WHERE "
		+ "Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='4')as abs4,(SELECT COUNT(*)FROM Dilg, stmd WHERE "
		+ "Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='6')as abs6,(SELECT COUNT(*) FROM stmd s "
		+ "LEFT OUTER JOIN Dilg d ON s.student_no=d.student_no WHERE d.Oid IS NULL AND "
		+ "s.depart_class =Class.ClassNo)as noabs FROM Class WHERE Type='P'");
		if(!cno.equals(""))sb.append("AND CampusNo='"+cno+"'");
		if(!sno.equals(""))sb.append("AND SchoolNo='"+sno+"'");
		if(!dno.equals(""))sb.append("AND DeptNo='"+dno+"'");
		if(!gno.equals(""))sb.append("AND Grade='"+gno+"'");
		if(!zno.equals(""))sb.append("AND SeqNo='"+zno+"'");
		//if(!abs.equals(""))sb.append("AND SeqNo='"+zno+"'");
		sb.append("ORDER BY ClassNo");
		List<Map>tmpList=df.sqlGet(sb.toString());			
		
		List<Map>list=new ArrayList();
		for(int i=0; i<tmpList.size(); i++){//無學生班級
			if(!tmpList.get(i).get("sts").toString().equals("0")){
				list.add(tmpList.get(i));
			}			
		}
		tmpList=null;		
		
		ListCls lc=new ListCls();
		lc.print(response, list, beginDate, endDate, getContext().getAttribute("school_year").toString(), getContext().getAttribute("school_term").toString());
		lc=null;
		return null;
	}
	
	/**
	 * 個人統計表
	 * @return
	 * @throws IOException
	 */
	public String listStd() throws IOException{		
		StringBuilder sql=new StringBuilder("SELECT c.ClassName, s.student_no,"
		+ "s.student_name,s.parent_name,s.CellPhone,s.telephone,"
		+ "count(case abs when '1' then 1 else null end)as abs1,"
		+ "count(case abs when '2' then 1 else null end)as abs2,"
		+ "count(case abs when '3' then 1 else null end)as abs3,"
		+ "count(case abs when '4' then 1 else null end)as abs4,"
		+ "count(case abs when '5' then 1 else null end)as abs5,"
		+ "count(case abs when '6' then 1 else null end)as abs6,"
		+ "count(case abs when '7' then 1 else null end)as abs7,"
		+ "count(case abs when '8' then 1 else null end)as abs8,"
		+ "count(case abs when '9' then 1 else null end)as abs9,"
		+ "COUNT(*)as abs FROM Class c, stmd s, Dilg d WHERE "
		+ "s.depart_class=c.ClassNo AND d.student_no=s.student_no "
		+ "AND d.date>='"+beginDate+"' AND d.date<='"+endDate+"'AND c.CampusNo='"+cno+"'");		
		if(!abs.equals(""))sql.append("AND d.abs='"+abs+"'");
		if(!sno.equals(""))sql.append("AND c.SchoolNo='"+sno+"'");
		if(!dno.equals(""))sql.append("AND c.DeptNo='"+dno+"'");
		if(!gno.equals(""))sql.append("AND c.Grade='"+gno+"'");
		if(!zno.equals(""))sql.append("AND c.SeqNo='"+zno+"'");		
		sql.append("GROUP BY d.student_no ");	
		if(!less.equals("")||!more.equals(""))sql.append("HAVING ");
		if(!less.equals(""))sql.append("abs"+abs+"<="+less+" ");			
		if(!more.equals("")){
			if(!less.equals("")){sql.append("AND ");
		}
			sql.append("abs"+abs+">"+more+" ");
		}				
		sql.append("ORDER BY c.ClassNo, s.student_no");		
		//System.out.println(sql);
		List<Map>list=df.sqlGet(sql.toString());		
		ListStd ls=new ListStd();
		ls.print(response, list, beginDate, endDate, getContext().getAttribute("school_year").toString(), getContext().getAttribute("school_term").toString());
		ls=null;
		return null;
	}
	
	/**
	 * 通知
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	public String listMail() throws IOException, ParseException{
		
		if(less.equals("")||more.equals("")||beginDate.equals("")||endDate.equals("")){
			Message msg=new Message();
			msg.setError("條件不完整");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		StringBuilder sql=new StringBuilder("SELECT(SELECT COUNT(*)FROM Dilg WHERE "
		+ "student_no=s.student_no AND date<='"+beginDate+"'AND abs='2')as beginCnt,"
		+ "(SELECT COUNT(*)FROM Dilg WHERE student_no=s.student_no "
		+ "AND date<='"+endDate+"' AND abs='2')as endCnt,"
		+ "c.ClassName,s.student_no, s.student_name, IFNULL(s.parent_name, s.student_name)as parent_name,"
		+ "s.curr_addr,s.curr_post FROM Class c,stmd s WHERE c.CampusNo='"+cno+"' AND c.ClassNo=s.depart_class ");
		
		if(!sno.equals(""))sql.append("AND c.SchoolNo='"+sno+"'");
		if(!dno.equals(""))sql.append("AND c.DeptNo='"+dno+"'");
		if(!gno.equals(""))sql.append("AND c.Grade='"+gno+"'");
		if(!zno.equals(""))sql.append("AND c.SeqNo='"+zno+"'");
		//不採用SQL過濾，改用程式過濾
		//sql.append("HAVING beginCnt>"+more+" AND endCnt>"+more+" AND "
		//+ "endCnt<"+less+" ORDER BY c.ClassNo");
		//sql.append("HAVING endCnt>"+more+" AND "
		//+ "endCnt<"+less+" ORDER BY c.ClassNo");
		sql.append("ORDER BY c.ClassNo, s.student_no");
		
		List<Map>stds=df.sqlGet(sql.toString());
		
		int beginCnt, endCnt, less=Integer.parseInt(this.less),  more=Integer.parseInt(this.more);		
		
		List <Map>list=new ArrayList();
		List tmp, tmp1=new ArrayList();
		//System.out.println(sql);
		for(int i=0; i<stds.size(); i++){
			endCnt=Integer.parseInt(stds.get(i).get("endCnt").toString());
			beginCnt=Integer.parseInt(stds.get(i).get("beginCnt").toString());
			//前期曠課少於或等於臨界低點，而且當期曠課多於或等於臨界低點，而且當期曠課少於臨界高點。
			if(beginCnt<more && endCnt>=more && endCnt<less){
					
				tmp=df.sqlGet("SELECT d.cls, DATE_FORMAT(d.date,'%m/%d')as date FROM Dilg d WHERE date<='"+endDate+"' AND d.abs='2' AND d.student_no='"+stds.get(i).get("student_no")+"'");
				stds.get(i).put("dilgs", tmp);
				tmp1.add(stds.get(i));
				
			}
			
		}
		
		
		ListMail lm=new ListMail();
		lm.print(response, tmp1, beginDate, endDate);
		lm=null;		
		return null;
	}
}