package br.com.leandro.crud.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.leandro.crud.Util;
import br.com.leandro.crud.data.Gender;
import br.com.leandro.crud.data.Person;
import br.com.leandro.crud.data.PersonImage;
import br.com.leandro.crud.service.PersonService;

@Scope(value = "session")
@Component(value = "personController")
@ELBeanName(value = "personController")
@Join(path = "/", to = "/person.jsf")
public class PersonController {

	@Autowired
	private PersonService personService;

	private List<Person> people;

	private List<Person> selectedPeople;

	private Person selectedPerson = new Person();

	// @ManagedProperty("#{facesContext}")
	// FacesContext faces;

	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		//FacesContext.getCurrentInstance().getApplication().getResourceHandler();
		people = personService.findAll();
	}

	/*
	 * @PostConstruct public void init() { loadData(); }
	 */

	public void save() {

		if (this.selectedPerson.getId() == null || this.selectedPerson.getId() == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Person Added"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Person Updated"));
		}

		personService.save(selectedPerson);
		selectedPerson = null;
		loadData();

		PrimeFaces.current().executeScript("PF('managePersonDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-people");
		// return "/person.xhtml?faces-redirect=true";
	}

	public void delete(Person p) {
		personService.delete(p);
		loadData();
	}

	public void delete() {

		personService.delete(selectedPerson);
		this.selectedPerson = null;
		loadData();

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Person Removed"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-people");
	}

	/*
	 * 
	 */
	public Gender[] getGenders() {
		return Gender.values();
	}

	public void openNew() {
		setSelectedPerson(new Person());
		// this.selectedPerson = new Person();
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedPeople()) {
			int size = this.selectedPeople.size();
			return size > 1 ? size + " people selected" : "1 person selected";
		}
		return "Delete";
	}

	public boolean hasSelectedPeople() {
		return this.selectedPeople != null && !this.selectedPeople.isEmpty();
	}

	public void deleteSelectedPeople() {
		this.people.removeAll(this.selectedPeople);
		this.selectedPeople = null;

		loadData();

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("People Removed"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-people");
		PrimeFaces.current().executeScript("PF('dtPeople').clearFilters()");
	}

	public Gender[] getGenderList() {
		return Gender.values();
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		
		if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {

			PersonImage personImages = new PersonImage();
			personImages.setImage(file.getContent());
			personImages.setPerson(selectedPerson);
			personImages.setNameImage(file.getFileName());
			personImages.setContentType(file.getContentType());
			selectedPerson.getPersonImages().add(personImages);

			FacesMessage msg = new FacesMessage("Successful", file.getFileName().trim() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage("messages", msg);
		}
	}

	public List<PersonImage> getPersonImages() {
		return selectedPerson.getPersonImages();
	}
	
	public byte[] getFirstImageOfSelectedPerson() {
		if (selectedPerson.getPersonImages() != null && !selectedPerson.getPersonImages().isEmpty())
			return selectedPerson.getPersonImages().iterator().next().getImage();
		else
			return null;
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Get ID value from actual request param.
			String personId = context.getExternalContext().getRequestParameterMap().get("personId");
			String imageIdString = context.getExternalContext().getRequestParameterMap().get("imageId");

			Long imageId = Long.parseLong(imageIdString);
			PersonImage personImage = null;

			Person person = personService.findById(Long.parseLong(personId));
			String contentType = null;
			if (imageId == 0) {
				if(person.getPersonImages().size() == 0)
					return null;
				personImage = person.getPersonImages().iterator().next();
				contentType = personImage.getContentType();

			} else {
				for (PersonImage pi : person.getPersonImages()) {
					if (pi.getId().equals(imageId)) {
						contentType = pi.getContentType();
						personImage = pi;
						break;
					}
				}
			}
			final PersonImage personImageEnclose = personImage;

			return DefaultStreamedContent.builder().contentType(contentType).stream(() -> {
				return new ByteArrayInputStream(Util.createThumbnail(personImageEnclose.getImage(), 160));
			}).build();
		}
	}

	public StreamedContent getImageByHashOfSelectedPerson() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			String hashCodeString = context.getExternalContext().getRequestParameterMap().get("hashCode");
			Integer hashCode = Integer.parseInt(hashCodeString);

			PersonImage personImage = null;
			for (PersonImage pi : selectedPerson.getPersonImages()) {
				if (hashCode == pi.hashCode()) {
					personImage = pi;
					break;
				}
			}

			final PersonImage personImageEnclose = personImage;
			String contentType = personImageEnclose.getContentType();

			return DefaultStreamedContent.builder().contentType(contentType).stream(() -> {
				return new ByteArrayInputStream(Util.createThumbnail(personImageEnclose.getImage(), 300));
			}).build();
		}
	}

	public void removeImage(AjaxBehaviorEvent event) {
		//UIComponent component = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());		
		try {
			String hashCodeString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hashCode");
			
			int hashCode = Integer.parseInt(hashCodeString);
	
			for (PersonImage pi : selectedPerson.getPersonImages()) {
				if (hashCode == pi.hashCode()) {
					selectedPerson.getPersonImages().remove(pi);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * 
	 */
	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public Person getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(Person selectedPerson) {
		if(selectedPerson.getId() != null && selectedPerson.getId() > 0) {
			// busca a pessoa atualizada no banco
			this.selectedPerson = personService.findById(selectedPerson.getId());
		} else {
			this.selectedPerson = selectedPerson;
		}
	}

	public List<Person> getSelectedPeople() {
		return selectedPeople;
	}

	public void setSelectedPeople(List<Person> selectedPeople) {
		this.selectedPeople = selectedPeople;
	}

}