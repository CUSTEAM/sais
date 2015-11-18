package action.rollCall;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.Dilg;
import model.DilgApply;
import model.Message;
import action.BaseAction;

/**
 * 缺曠管理
 * @author John
 */
public class TimeOffManagerAction extends BaseAction{
	
	public String student_no;
	public String nameno;
	public String beginDate;
	public String endDate;
	
	public String Oid[];
	public String abs[];
	
	public int begin;
	public int end;
	public String absType;
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");	
	public String execute(){	
		
		return SUCCESS;
	}
	
	public String search(){
		if(student_no.equals("")){
			Message m=new Message();
			m.setError("輸入不完整");
			savMessage(m);
			return execute();
		}		
		List<Map>list=sam.getCsDilgDetail(student_no, absType, beginDate, endDate);
		request.setAttribute("dilgs", list);		
		request.setAttribute("info", sam.StudentDilg(student_no));
		request.setAttribute("cs", sam.getDilgDetail(student_no, getContext().getAttribute("school_term").toString()));
		request.setAttribute("just", df.sqlGetStr("SELECT total_score FROM Just WHERE student_no='"+student_no+"'"));//判斷該生是否已結算
		request.setAttribute("dilgHist", sam.getDilgRecord(student_no));
		request.setAttribute("failSeld", sam.getFailStd(student_no));//已標記扣考
		request.setAttribute("endStart", sam.getEndAtStart(student_no));		
		return SUCCESS;
	}
	
	public String delete(){		
		
		return search();
	}
	
	public String update(){
		
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
					//df.exSql("DELETE FROM Dilg_apply WHERE Oid=(SELECT Dilg.Dilg_app_oid FROM Dilg WHERE Dilg.Oid="+Oid[i]+")");					
				}
				//改為遲到
				if(abs[i].equals("5")){
					sam.saveRecord(Oid[i], getSession().getAttribute("userid").toString(), "修改為遲到");//留底
					df.exSql("UPDATE Dilg SET abs='5', Dilg_app_oid=null WHERE Oid="+Oid[i]);
					//df.exSql("DELETE FROM Dilg_apply WHERE Oid=(SELECT Dilg.Dilg_app_oid FROM Dilg WHERE Dilg.Oid="+Oid[i]+")");					
				}
			}
		}		
		return search();
	}

	public String add() throws Exception {
		if(student_no.trim().equals("")||beginDate.trim().equals("")||endDate.trim().equals("")||begin==0||end==0){
			Message m=new Message();
			m.setError("輸入不完整");
			savMessage(m);
			return execute();
		}
		
		if(begin>end){
			Message m=new Message();
			m.setError("節次矛盾");
			savMessage(m);
			return execute();
		}
		
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
			da.setStudent_no(student_no);
			da.setRealLevel("1");
			da.setResult("1");
			da.setDefaultLevel("1");
			da.setCr_date(new Date());
			df.update(da);
		}
		
		List <Map>dClass;
		boolean checkEmpty=false;
		int week=b.get(Calendar.DAY_OF_WEEK)-1;
		if(week==0) week=7; //星期日
		for(int i=0; i<=days; i++){
			dClass=df.sqlGet("SELECT dc.* FROM Dtime d,Seld s,Dtime_class dc WHERE d.Sterm='"+getContext().getAttribute("school_term")+"'AND " +
			"d.Oid=s.Dtime_oid AND d.Oid=dc.Dtime_oid AND dc.week='"+week+"'AND((dc.begin>="+begin+" AND dc.begin<="+end+")OR " +
			"(dc.end>="+begin+" AND dc.end<="+end+")) AND s.student_no='"+student_no+"'");			
			
			if(dClass.size()>0){
				checkEmpty=true;
			}
			//建立缺曠
			for(int j=0; j<dClass.size(); j++){
				if(da!=null){
					saveDilg(student_no, dClass.get(j), b.getTime(), da.getOid(), absType);
				}else{
					saveDilg(student_no, dClass.get(j), b.getTime(), null, absType);
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
		return search();		
	}
	
	public void saveDilg(String student_no, Map dc, Date date, Integer daOid, String absType){
		
		int begin=Integer.parseInt(dc.get("begin").toString());
		int end=Integer.parseInt(dc.get("end").toString());
		Dilg d;
		for(int i=begin; i<=end; i++){
			d=new Dilg();//建立缺課記錄
			d.setAbs(absType);
			d.setCls(String.valueOf(i));
			d.setDate(date);
			d.setDtime_oid(Integer.parseInt(dc.get("Dtime_oid").toString()));
			d.setStudent_no(student_no);
			d.setDilg_app_oid(daOid);
			try{
				df.update(d);
			}catch(Exception e){
				//糙尼瑪的只有重複請才會有這種情況!				
				String seldOid=df.sqlGetStr("SELECT Oid FROM Seld WHERE student_no='"+student_no+"' AND Dtime_oid="+dc.get("Dtime_oid"));
				df.exSql("UPDATE Seld SET dilg_period=dilg_period-1 WHERE Oid="+seldOid);
				df.exSql("DELETE FROM Dilg WHERE student_no='"+student_no+"' AND date='"+sf.format(date)+"' AND cls='"+i+"'");
				df.update(d);
			}			
		}
	}
}