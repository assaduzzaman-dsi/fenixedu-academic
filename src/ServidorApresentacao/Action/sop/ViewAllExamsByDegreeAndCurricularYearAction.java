package ServidorApresentacao.Action.sop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import DataBeans.InfoExecutionDegree;
import DataBeans.InfoExecutionPeriod;
import DataBeans.InfoViewAllExams;
import ServidorAplicacao.GestorServicos;
import ServidorAplicacao.IUserView;
import ServidorApresentacao.Action.sop.utils.ServiceUtils;
import ServidorApresentacao.Action.sop.utils.SessionConstants;
import ServidorApresentacao.Action.sop.utils.SessionUtils;

/**
 * @author Luis Cruz e Sara Ribeiro
 */
public class ViewAllExamsByDegreeAndCurricularYearAction extends Action {
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession(false);
		if (session != null) {
			GestorServicos gestor = GestorServicos.manager();
			IUserView userView =
				(IUserView) session.getAttribute(SessionConstants.U_VIEW);

			InfoExecutionPeriod infoExecutionPeriod = setExecutionContext(request);

			// 1. Read All ExecutionDegrees
			Object[] argsReadExecutionDegrees =	{infoExecutionPeriod.getInfoExecutionYear() };			
			List infoExecutionDegreesList =
				(List) gestor.executar(
					userView,
					"ReadExecutionDegreesByExecutionYear",
					argsReadExecutionDegrees);

			// 2. For each ExecutionDegree and for each CurricularYear
			//    readExamsByExecutionDegreeAndCurricularYear 
			int numberOfCurricularYears = 5;
			List infoViewAllExamsList = new ArrayList();
			
			for (int i = 0; i < infoExecutionDegreesList.size(); i++) {
				InfoExecutionDegree infoExecutionDegree =
					(InfoExecutionDegree) infoExecutionDegreesList.get(i);
				for (int j = 1; j < numberOfCurricularYears+1; j++) {
					InfoViewAllExams infoViewAllExams = new InfoViewAllExams(infoExecutionDegree,new Integer(j),null);					
					Object[] args =	{infoExecutionDegree,infoExecutionPeriod, new Integer(j) };
					List infoExecutionCourseAndExamsList =
						(List) gestor.executar(
							userView,"ReadExamsByExecutionDegreeAndCurricularYear",	args);
					if(infoExecutionCourseAndExamsList!= null && infoExecutionCourseAndExamsList.isEmpty()) {
						infoViewAllExams.setInfoExecutionCourseAndExamsList(null);
					} else {
						infoViewAllExams.setInfoExecutionCourseAndExamsList(infoExecutionCourseAndExamsList);
					}						
					infoViewAllExamsList.add(infoViewAllExams);
				}						
			}

			if (infoViewAllExamsList != null
				&& infoViewAllExamsList.isEmpty()) {
				request.removeAttribute(SessionConstants.ALL_INFO_EXAMS_KEY);
			} else {
				request.setAttribute("infoExecutionDegreesList",infoExecutionDegreesList);
				request.setAttribute(
					SessionConstants.ALL_INFO_EXAMS_KEY,
					infoViewAllExamsList);
			}

			return mapping.findForward("Sucess");
		} else
			throw new Exception();
		// nao ocorre... pedido passa pelo filtro Autorizacao
	}
	
	/**
	 * Method setExecutionContext.
	 * @param request
	 */
	private InfoExecutionPeriod setExecutionContext(HttpServletRequest request)
		throws Exception {

		HttpSession session = request.getSession(false);
		InfoExecutionPeriod infoExecutionPeriod =
			(InfoExecutionPeriod) session.getAttribute(
				SessionConstants.INFO_EXECUTION_PERIOD_KEY);
		if (infoExecutionPeriod == null) {
			IUserView userView = SessionUtils.getUserView(request);
			infoExecutionPeriod =
				(InfoExecutionPeriod) ServiceUtils.executeService(
					userView,
					"ReadActualExecutionPeriod",
					new Object[0]);

			session.setAttribute(
				SessionConstants.INFO_EXECUTION_PERIOD_KEY,
				infoExecutionPeriod);
		}
		return infoExecutionPeriod;
	}
	
	
}
