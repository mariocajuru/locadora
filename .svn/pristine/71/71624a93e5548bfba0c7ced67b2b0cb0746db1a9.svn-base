package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.Session;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Vistoria;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.VistoriaRN;
import br.com.locadora.util.HibernateUtil;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "vistoriaBean")
@ViewScoped
public class VistoriaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9030917088115821711L;

	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private Imovel imovel = new Imovel();
	private Funcionario funcionario = new Funcionario();
	private Vistoria vistoria = new Vistoria();
	private List<Vistoria> listaVistorias;

	private Date diaVisita;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VistoriaBean() {
		eventModel = new DefaultScheduleModel();
		/*
		 * eventModel.addEvent(new DefaultScheduleEvent("Renovar", new Date(),
		 * new Date()));
		 */

		Session sessao = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Vistoria> v = new ArrayList();
		v = sessao.createCriteria(Vistoria.class).list();
		for (Vistoria sc : v) {
			if (sc.getVisPendente().equals('S')) {
				eventModel.addEvent(new DefaultScheduleEvent(sc
						.getVisObservacao(), sc.getVisDataAgenda(), sc
						.getVisDataAgenda(), "" + sc.getVisId()));
			}
			// eventModel.addEvent(new
			// DefaultScheduleEvent("Codigo: "+sc.getVisId()+" OBS: "+sc.getVisObservacao(),
			// sc.getVisDataAgenda(),sc.getVisDataAgenda()));
			// System.out.println("Obs :"+sc.getVisObservacao()+" Date: "+sc.getVisDataAgenda());
		}

	}

	public void salvarReditar() {
		/*
		 * System.out.println("Id do imovel: " + imovel.getImoId());
		 * System.out.println("Event: " + event.getEndDate() + " final date: " +
		 * event.getStartDate());
		 */
		/*
		 * System.out.println("OBS: " + vistoria.getVisObservacao());
		 * System.out.println("Pendente: " + vistoria.getVisPendente());
		 * System.out.println("Data da Visista: " +
		 * vistoria.getVisDataVisita());
		 */

		/*
		 * Funcionario fun = new Funcionario(); fun.setFunId(1);
		 * this.vistoria.setFuncionario(fun);
		 * this.vistoria.setVisDataAgenda(event.getEndDate());
		 * this.vistoria.setVisPendente('S'); this.vistoria.setImovel(imovel);
		 */

		// this.vistoria.setVisDataVisita(diaVisita);

		VistoriaRN vistoriaRN = new VistoriaRN();
		vistoriaRN.salvar(this.vistoria);

		if (vistoria.getVisPendente() == 'N') {
			if (vistoria.getImovel().getImoEfetivo()== false) {
				vistoria.getImovel().setImoDataRejeicao(new Date());

				System.out.println("OK");
			}
			ImovelRN imovelRN = new ImovelRN();
			imovelRN.salvar(vistoria.getImovel());
		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Alterado com sucesso"));

		this.imovel = new Imovel();
		this.funcionario = new Funcionario();
		this.listaVistorias = null;
		this.vistoria = new Vistoria();
	}

	public void salvar() {
		/*
		 * System.out.println("Id do imovel: " + imovel.getImoId());
		 * System.out.println("Event: " + event.getEndDate() + " final date: " +
		 * event.getStartDate()); System.out.println("OBS: " +
		 * vistoria.getVisObservacao());
		 */

		Funcionario fun = new Funcionario();
		fun.setPesId(1);
		this.vistoria.setFuncionario(fun);
		this.vistoria.setVisDataAgenda(event.getEndDate());
		this.vistoria.setVisPendente('S');
		this.vistoria.setImovel(imovel);

		VistoriaRN vistoriaRN = new VistoriaRN();
		vistoriaRN.salvar(this.vistoria);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com sucesso"));

		this.imovel = new Imovel();
		this.funcionario = new Funcionario();
		this.listaVistorias = null;
		this.vistoria = new Vistoria();
	}

	public Vistoria getVistoria() {
		return vistoria;
	}

	public void setVistoria(Vistoria vistoria) {
		this.vistoria = vistoria;
	}

	public List<Vistoria> getListaVistorias() {
		if (listaVistorias == null) {
			VistoriaRN vistoriaRN = new VistoriaRN();
			this.listaVistorias = vistoriaRN.listar();
		}

		return listaVistorias;
	}

	public void setListaVistorias(List<Vistoria> listaVistorias) {
		this.listaVistorias = listaVistorias;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();

		VistoriaRN vistoriaRN = new VistoriaRN();
		this.vistoria = vistoriaRN.carregar(Integer.parseInt(event
				.getStyleClass()));
		/*
		 * System.out.println("Vistoria ID: " + vistoria.getVisId() + " OBS: " +
		 * vistoria.getVisObservacao() + " Data: " +
		 * vistoria.getVisDataAgenda());
		 */
	}

	public void onDateSelect(SelectEvent selectEvent) {

		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());

		this.imovel = new Imovel();
		this.funcionario = new Funcionario();
		this.listaVistorias = null;
		this.vistoria = new Vistoria();

		// System.out.println("Novo cadastro em uma nova data ");
	}

	public void teste() {
		System.out.println("Id do imovel: " + imovel.getImoId());
		System.out.println("Event: " + event.getEndDate() + " final date: "
				+ event.getStartDate());
		System.out.println("OBS: " + vistoria.getVisObservacao());

	}

	@SuppressWarnings("deprecation")
	public void onEventMove(ScheduleEntryMoveEvent event) {
		VistoriaRN vistoriaRN = new VistoriaRN();
		this.vistoria = vistoriaRN.carregar(Integer.parseInt(event
				.getScheduleEvent().getStyleClass()));
		Date d = vistoria.getVisDataAgenda();
		d.setMinutes(d.getMinutes() + event.getMinuteDelta());
		d.setDate(vistoria.getVisDataAgenda().getDate() + event.getDayDelta());
		// System.out.println(vistoria.getVisDataAgenda().getDate());
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"dd/MM/yyyy hh:mm");
		this.vistoria.setVisDataAgenda(d);
		vistoriaRN.salvar(vistoria);

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Agenda Modificada", " Dia e horario modificado: "
						+ sdf.format(d) + ", \n OBS das Vistoria: "
						+ vistoria.getVisObservacao());
		addMessage(message);

	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		/*
		 * FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		 * "Agenda Modificada", " Modificando o horario: " + event.getDayDelta()
		 * + " , Horario modificado: " + event.getMinuteDelta());
		 * addMessage(message);
		 * System.out.println("Mudando o horario no onEventResize ");
		 * System.out.println("Id do imovel: " + imovel.getImoId());
		 * System.out.println("Event date: " + event.getDayDelta() +
		 * " Horario: " + event.getMinuteDelta()); System.out.println("OBS: " +
		 * vistoria.getVisObservacao());
		 */
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDiaVisita() {
		return diaVisita;
	}

	public void setDiaVisita(Date diaVisita) {
		this.diaVisita = diaVisita;
	}

}
