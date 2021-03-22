package action.doc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import model.DilgApply;
import model.DilgApplyHist;
import action.BaseAction;

public class DilgAppManagerAction extends BaseAction{
	
	public String execute() throws Exception {		
		/*List list=null;
		if(request.getParameter("type")!=null){
			if(request.getParameter("type").equals("redirect")){
				//轉呈假單列表
				list=df.sqlGet("SELECT e.cname, dr.name, d.*, s.student_no, s.student_name, c.ClassName FROM Dilg_apply d LEFT OUTER JOIN empl e ON e.idno=d.auditor, stmd s, Class c," +
				"Dilg_rules dr, Dilg_apply_hist dh WHERE dh.Dilg_app_oid=d.Oid AND dr.id=d.abs AND c.ClassNo=s.depart_class AND " +
				"s.student_no=d.student_no AND dh.auditor='"+getSession().getAttribute("userid")+"'");				
				
				for(int i=0; i<list.size(); i++){							
					((Map)list.get(i)).put("abss", 
					df.sqlGet("SELECT c.chi_name, s.dilg_period, d.date, d.cls, d.abs FROM Dilg d, Seld s, Csno c, Dtime dt WHERE " +
					"dt.cscode=c.cscode AND dt.Oid=d.Dtime_oid AND s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid AND " +
					"d.Dilg_app_oid="+((Map)list.get(i)).get("Oid")));
				}
				
			}		
			
			if(request.getParameter("type").equals("pass")){
				//已核假單列表
				list=df.sqlGet("SELECT dr.name, d.*, s.student_no, s.student_name, c.ClassName FROM Dilg_apply d, stmd s, Class c, Dilg_rules dr WHERE " +
				"dr.id=d.abs AND c.ClassNo=s.depart_class AND s.student_no=d.student_no AND d.result IS NOT NULL AND d.auditor='"+
				getSession().getAttribute("userid")+"'");				
				
				for(int i=0; i<list.size(); i++){
					((Map)list.get(i)).put("abss", 
					df.sqlGet("SELECT c.chi_name, s.dilg_period, d.date, d.cls, d.abs FROM Dilg d, Seld s, Csno c, Dtime dt " +
					"WHERE dt.cscode=c.cscode AND dt.Oid=d.Dtime_oid AND s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid AND " +
					"d.Dilg_app_oid="+((Map)list.get(i)).get("Oid")));				
				}
				
			}
		}
		
		if(request.getParameter("type")==null){
			//未核假單列表
			list=df.sqlGet("SELECT dr.name, d.*, s.student_no, s.student_name, c.ClassName FROM Dilg_apply d, stmd s, Class c, Dilg_rules dr WHERE " +
			"dr.id=d.abs AND c.ClassNo=s.depart_class AND s.student_no=d.student_no AND d.result IS NULL AND d.auditor='"+getSession().getAttribute("userid")+"'");
			
			
			for(int i=0; i<list.size(); i++){
				((Map)list.get(i)).put("abss", 
				df.sqlGet("SELECT c.chi_name, s.dilg_period, d.date, d.cls, d.abs FROM Dilg d, Seld s, Csno c, Dtime dt " +
				"WHERE dt.cscode=c.cscode AND dt.Oid=d.Dtime_oid AND s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid AND " +
				"d.Dilg_app_oid="+((Map)list.get(i)).get("Oid")));
			}
			
		}
		
		getSession().setAttribute("dilgs", list);	*/	
		return SUCCESS;
	}
	
	/**
	 * 審核
	 * @return
	 * @throws Exception
	 */
	public String checkout() throws Exception{
		
		for(int i=0; i<Oid.length; i++){
			if(!result[i].equals("")){				
				//不核準的情況，無需後送直接寫為不核準
				if(result[i].equals("2")){
					df.exSql("UPDATE Dilg_apply SET result='2', reply='"+reply[i]+"' WHERE Oid="+Oid[i]);//假單狀態改為不核	
					df.exSql("UPDATE Dilg SET abs='2' WHERE Dilg_app_oid="+Oid[i]);//假單中的課均改為缺課
				}else{					
					//核准的情況
					DilgApply d=(DilgApply)df.hqlGetListBy("FROM DilgApply WHERE Oid="+Oid[i]).get(0);					
					if(d.getDefaultLevel().equals(d.getRealLevel())){
						//若預設層級與目前層級相同
						d.setResult("1");//可以判斷結果
						d.setReply(reply[i]);
						df.update(d);//寫入結果並結案
						df.exSql("UPDATE Dilg SET abs='"+d.getAbs()+"' WHERE Dilg_app_oid="+Oid[i]);//假單中的課均改為假別
					}else{
						d.setRealLevel(String.valueOf(Integer.parseInt(d.getRealLevel())+1));	
						//尋找下一層審核者
						String nexter;
						if(d.getRealLevel().equals("2")){
							//2階改為系主任
							//nexter=df.sqlGetStr("SELECT cd.director FROM CODE_DEPT cd, Class c, stmd s WHERE cd.id=c.DeptNo AND c.ClassNo=s.depart_class AND s.student_no='"+d.getStudent_no()+"'");
							//2階改為副主任
							nexter=df.sqlGetStr("SELECT cd.director_deputy FROM CODE_DEPT cd, Class c, stmd s WHERE cd.id=c.DeptNo AND c.ClassNo=s.depart_class AND s.student_no='"+d.getStudent_no()+"'");
							//為防止系主任為空缺
							if(nexter==null||nexter.equals("")){
								nexter=df.sqlGetStr("SELECT d.idno FROM stmd s, Dilg_charge d, Class c WHERE " +
								"s.depart_class=c.ClassNo AND d.CampusNo=c.CampusNo AND d.SchoolType=c.SchoolType AND " +
								//"d.level='"+d.getRealLevel()+"' AND s.student_no='"+d.getStudent_no()+"'");
								"d.level='2' AND s.student_no='"+d.getStudent_no()+"'");
							}						
						}else{//3階學務長？							
							nexter=df.sqlGetStr("SELECT d.idno FROM stmd s, Dilg_charge d, Class c WHERE " +
							"s.depart_class=c.ClassNo AND d.CampusNo=c.CampusNo AND d.SchoolType=c.SchoolType AND " +
							//"d.level='"+d.getRealLevel()+"' AND s.student_no='"+d.getStudent_no()+"'");
							"d.level='3' AND s.student_no='"+d.getStudent_no()+"'");
						}						
						//預設層級與目前層級不同時需後送，不寫入結果但要寫入歷程
						d.setResult(null);//後送待審中
						d.setReply(reply[i]);
											
						d.setAuditor(nexter);
						df.update(d);//後送						
						df.exSql("UPDATE Dilg SET abs='"+d.getAbs()+"' WHERE Dilg_app_oid="+Oid[i]);//假單中的課均改為假別
						//建立歷程
						DilgApplyHist dah=new DilgApplyHist();
						dah.setAuditor((String)getSession().getAttribute("userid"));
						dah.setDate(new Date());
						dah.setDilg_app_oid(d.getOid());
						df.update(dah);//寫入歷程
					}					
				}				
			}			
		}		
		return execute();
	}
	
	public String search(){
		
		StringBuilder sb;
		
		
		
		sb=new StringBuilder("SELECT dr.name, d.*, s.student_no, s.student_name, c.ClassName FROM Dilg_apply d, stmd s, Class c, Dilg_rules dr WHERE " +
		"dr.id=d.abs AND c.ClassNo=s.depart_class AND s.student_no=d.student_no AND d.result IS NOT NULL ");		
		if(!begin.trim().equals("")){sb.append("AND d.cr_date>='"+begin+"'");}		
		if(!end.trim().equals("")){sb.append("AND d.cr_date<='"+end+"'");}		
		if(!stdNo.trim().equals("")){sb.append("AND d.student_no='"+stdNo+"'");}		
		sb.append("ORDER BY d.cr_date DESC");	
		
		
		System.out.println(sb);
		
		List list=df.sqlGet(sb.toString());
		for(int i=0; i<list.size(); i++){
			((Map)list.get(i)).put("abss", 
			df.sqlGet("SELECT c.chi_name, s.dilg_period, d.date, d.cls, d.abs FROM Dilg d, Seld s, Csno c, Dtime dt " +
			"WHERE dt.cscode=c.cscode AND dt.Oid=d.Dtime_oid AND s.student_no=d.student_no AND s.Dtime_oid=d.Dtime_oid AND " +
			"d.Dilg_app_oid="+((Map)list.get(i)).get("Oid")+""));				
		}
		getSession().setAttribute("dilgs", list);
		return SUCCESS;
	}
	
	
	public String begin;
	public String end;
	public String stdNo;
	public String nameno;
	
	public String Oid[];
	public String result[];
	public String reply[];
}
