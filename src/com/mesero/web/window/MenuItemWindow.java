package com.mesero.web.window;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;


import com.mesero.bean.Ingrediente;
import com.mesero.bean.Menu;
import com.mesero.bean.MenuItem;
import com.mesero.core.ImageUploader;
import com.mesero.core.Status;
import com.mesero.manageBean.ManageIngrediente;
import com.mesero.manageBean.ManageMenuItem;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MenuItemWindow extends Window {
	
	
	private VerticalLayout mainLayout = null;
	
	private VerticalLayout imgLayout = null;
	private VerticalLayout centerContentLayout = null;
	private VerticalLayout ingredientesContentLayout = null;
	private HorizontalLayout footerLayout = null;
	private HorizontalLayout headerLayout = null;
	
	private FormLayout formLayout = null;
	
	private Panel centerPanel = null;
	private Panel ingredientesPanel = null;
	private Panel fotoPanel = null;
	
	private Label headerLabel = null;
	
	private Button aceptarButton = null;
	private Button cancelarButton = null;
	
	private TwinColSelect ingredientesField = null;
	
	private TextField nombreTextField = null;
	private TextField precioTextField = null;
	
	private Upload fotoUpload = null;
	
	private CheckBox hayCheckBox = null;
	
	private ImageUploader imageUploader = new ImageUploader(getImgLayout(),"images");
	
	private MenuItem menuItem = null;
	private Menu menu = null;
	
	private ManageMenuItem manageMenuItem = new ManageMenuItem();
	private ManageIngrediente manageIngrediente = new ManageIngrediente();
	
	public MenuItemWindow(MenuItem menuItem, Menu menu) {
		
		this.menuItem = menuItem;
		this.menu = menu;
		
		setWidth(500, Sizeable.Unit.PIXELS);
		setHeight(600, Sizeable.Unit.PIXELS);
		
		setResizable(false);
		setModal(true);
		setContent(getMainLayout());

		if(this.menuItem != null) { 
			setData();
			getHeaderLabel().setValue("Editar el Item del Menu");
		} else {
			getHeaderLabel().setValue("Nuevo Item del Menu");
		}
	}
	
	private VerticalLayout getMainLayout() {
		if(mainLayout == null) {
			mainLayout = new VerticalLayout();
			mainLayout.addComponent(getHeaderLayout());
			mainLayout.setComponentAlignment(getHeaderLayout(), Alignment.TOP_CENTER);
			mainLayout.addComponent(getCenterPanel());
			mainLayout.setComponentAlignment(getCenterPanel(), Alignment.MIDDLE_CENTER);
			mainLayout.addComponent(getFooterLayout());
			mainLayout.setComponentAlignment(getFooterLayout(), Alignment.BOTTOM_CENTER);
			mainLayout.setMargin(false);
		}
		return mainLayout;
	}
	
	private VerticalLayout getCenterContentLayout() {
		if(centerContentLayout == null) {
			centerContentLayout = new VerticalLayout();
			centerContentLayout.setMargin(true);
			centerContentLayout.setSpacing(true);
			centerContentLayout.addComponents(getFormLayout(),getIngredientesPanel());
			///centerContentLayout.setExpandRatio(getIngredientesPanel(),1.0f);
			
		}
		return centerContentLayout;
	}
	
	private VerticalLayout getIngredientesContentLayout() {
		if(ingredientesContentLayout == null) {
			ingredientesContentLayout = new VerticalLayout();
			ingredientesContentLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			ingredientesContentLayout.setHeight(100,Sizeable.Unit.PERCENTAGE);
			ingredientesContentLayout.setMargin(true);
			ingredientesContentLayout.setSpacing(true);
			ingredientesContentLayout.addComponent(getIngredientesField());
		}
		return ingredientesContentLayout;
	}
	
	private VerticalLayout getImgLayout() {
		if(imgLayout == null) {
			imgLayout = new VerticalLayout();
			imgLayout.setMargin(true);
			imgLayout.setSpacing(false);
			imgLayout.setWidth(100,Sizeable.Unit.PERCENTAGE);
			imgLayout.setHeight(100,Sizeable.Unit.PERCENTAGE);
			
		}
		return imgLayout;
	}
	
	private FormLayout getFormLayout() {
		if(formLayout == null) {
			formLayout = new FormLayout();
			formLayout.setSpacing(true);
			formLayout.setMargin(false);
			formLayout.addComponents(getNombreTextField(),getPrecioTextField(),getFotoPanel(),getFotoUpload(),getHayCheckBox());
		}
		return formLayout;
	}
	
	private HorizontalLayout getHeaderLayout() {
		if(headerLayout == null) {
			headerLayout = new HorizontalLayout();
			headerLayout.addComponent(getHeaderLabel());
		}
		return headerLayout;
	}
	
	private HorizontalLayout getFooterLayout() {
		if(footerLayout == null) {
			footerLayout = new HorizontalLayout();
			footerLayout.setSpacing(true);
			
			footerLayout.addComponent(getAceptarButton());
			footerLayout.setComponentAlignment(getAceptarButton(), Alignment.MIDDLE_CENTER);
			footerLayout.addComponent(getCancelarButton());
			footerLayout.setComponentAlignment(getCancelarButton(), Alignment.MIDDLE_CENTER);
			
			footerLayout.setHeight(70,Sizeable.Unit.PIXELS);
		}
		return footerLayout;
	}

	private Panel getIngredientesPanel() {
		if(ingredientesPanel == null) {
			ingredientesPanel = new Panel("Ingredientes");
			ingredientesPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			//ingredientesPanel.setHeight(465, Sizeable.Unit.PIXELS);
			ingredientesPanel.setContent(getIngredientesContentLayout());
		}
		return ingredientesPanel;
	}
	
	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel();
			centerPanel.setWidth(100, Sizeable.Unit.PERCENTAGE);
			centerPanel.setHeight(465, Sizeable.Unit.PIXELS);
			centerPanel.setContent(getCenterContentLayout());
		}
		return centerPanel;
	}
	
	private Panel getFotoPanel() {
		if(fotoPanel == null) {
			fotoPanel = new Panel("Foto");
			fotoPanel.setWidth(300, Sizeable.Unit.PIXELS);
			fotoPanel.setHeight(200, Sizeable.Unit.PIXELS);
			fotoPanel.setContent(getImgLayout());
		}
		return fotoPanel;
	}
	
	
	
	private TextField getNombreTextField() {
		if(nombreTextField == null) {
			nombreTextField = new TextField("Nombre");
			//nombreTextField.setMaxLength(Factura.ID_MAX_LENGTH);
			nombreTextField.setRequired(true);
		}
		return nombreTextField;
	}
	
	private TextField getPrecioTextField() {
		if(precioTextField == null) {
			precioTextField = new TextField("Precio");
			//nombreTextField.setMaxLength(Factura.ID_MAX_LENGTH);
			precioTextField.setRequired(true);
		}
		return precioTextField;
	}
	
	private CheckBox getHayCheckBox() {
		if(hayCheckBox == null) {
			hayCheckBox = new CheckBox("Disponible");
			
		}
		return hayCheckBox;
	}
	
	private TwinColSelect getIngredientesField() {
		if(ingredientesField == null) {
			ingredientesField = new TwinColSelect();
			ingredientesField.setRows(6);
			ingredientesField.setWidth(100,Sizeable.Unit.PERCENTAGE);
			ingredientesField.setHeight(100,Sizeable.Unit.PERCENTAGE);
			ingredientesField.setNullSelectionAllowed(true);
			ingredientesField.setMultiSelect(true);
			ingredientesField.setImmediate(true);
			ingredientesField.setLeftColumnCaption("Disponibles");
			ingredientesField.setRightColumnCaption("Seleccionados");
			ingredientesField.addItems(manageIngrediente.listIngrediente());
		}
		return ingredientesField;
	}
	
	private Upload getFotoUpload() {
		if(fotoUpload == null) {
			fotoUpload = new Upload("Foto", imageUploader);
			fotoUpload.addSucceededListener(new SucceededListener() {
				@Override
				public void uploadSucceeded(SucceededEvent event) {
					// TODO Auto-generated method stub
					final Image image = new Image(null, new StreamResource(new StreamSource() {
			    		@Override
			    		public InputStream getStream() {
			    			try {
								return  new FileInputStream(imageUploader.file.getAbsoluteFile());
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
			    		}	
					},imageUploader.file.getAbsolutePath()));
			    	
			    	image.setWidth(100, Sizeable.Unit.PERCENTAGE);
					image.setHeight(100, Sizeable.Unit.PERCENTAGE);
					
					getImgLayout().removeAllComponents();
			    	getImgLayout().addComponent(image);
			    	getImgLayout().setComponentAlignment(image, Alignment.MIDDLE_CENTER);
				}
			});
		}
		return fotoUpload;
	}
	
	private Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label();
			headerLabel.setStyleName("h2");
		}
		return headerLabel;
	}
	
	private Button getAceptarButton() {
		if(aceptarButton == null) {
			aceptarButton = new Button("Aceptar");
			aceptarButton.setWidth(150, Sizeable.Unit.PIXELS);
			aceptarButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					procesar();
				}
			});
		}
		return aceptarButton;
	}
	
	private Button getCancelarButton() {
		if(cancelarButton == null) {
			cancelarButton = new Button("Cancelar");
			cancelarButton.setWidth(150, Sizeable.Unit.PIXELS);
			cancelarButton.addClickListener(new ClickListener() {
				 @Override
				 public void buttonClick(final ClickEvent event) {
					 close();
				 }
			});
		}
		return cancelarButton;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	private void setData() {
		getNombreTextField().setValue(menuItem.getNombre_menuItem());
		getNombreTextField().setReadOnly(true);
		getPrecioTextField().setValue(new Double(menuItem.getPrecio()).toString());
		/*
		ArrayList <String> values = new ArrayList <String>();
		
		for(Ingrediente ingrediente:menuItem.getIngredientes()) {
			values.add(ingrediente.getNombre());	
		}*/
		
		/*System.out.println("ing = "+menuItem.getIngredientes().size());
		System.out.println("ing = "+getIngredientesField().getContainerDataSource().getItemIds().size());
		*/
		ArrayList <Ingrediente> values = new ArrayList <Ingrediente>();
		
		for(Ingrediente ingrediente : (Collection<Ingrediente>)getIngredientesField().getContainerDataSource().getItemIds()) {
			for(Ingrediente ingredienteX :menuItem.getIngredientes()) {
				if(ingredienteX.getNombre().equals(ingrediente.getNombre())) {
					values.add(ingrediente);
				}
			}
		}
		
		getIngredientesField().setValue(values);
		
		File imgFile = new File(Status.getInstance().basePath+File.separator+"images"+File.separator+menuItem.getImageName());
		
		if(!imgFile.exists()) {
			imgFile = new File(Status.getInstance().basePath+File.separator+"images"+File.separator+"default.img");
		}
		
		Image img = new Image(null,new FileResource(imgFile));
		img.setWidth(100, Sizeable.Unit.PERCENTAGE);
		img.setHeight(100, Sizeable.Unit.PERCENTAGE);
		getImgLayout().removeAllComponents();
		getImgLayout().addComponent(img);
		getImgLayout().setComponentAlignment(img, Alignment.MIDDLE_CENTER);
		
		getHayCheckBox().setValue(menuItem.isHay() == 1);
	}
	
	private boolean Validar() {
		
		try {
			getNombreTextField().validate();
		} catch (InvalidValueException e) {
		    Notification.show("El valor del Nombre no puede ser vacio.", Notification.Type.TRAY_NOTIFICATION);
		    getNombreTextField().setValidationVisible(true);
		    return false;
		}
		
		try {
			getPrecioTextField().validate();
			double precio = new Double(getPrecioTextField().getValue());
			if(precio < 0 && precio > 10000000) {
				throw new InvalidValueException("");
			}
		} catch (NumberFormatException ne) {
			Notification.show("El Precio no puede ser vacio y debe ser un valor decimal positivo en el rango de 1 y 10000000.", Notification.Type.TRAY_NOTIFICATION);
			getPrecioTextField().setValidationVisible(true);
			return false;
		} catch (InvalidValueException e) {
		    Notification.show("El Precio no puede ser vacio y debe ser un valor decimal positivo en el rango de 1 y 10000000.", Notification.Type.TRAY_NOTIFICATION);
		    getPrecioTextField().setValidationVisible(true);
		    return false;
		}
		
		return true;
	}
	
	private void cargarIngredientes(MenuItem menuItem) {
		
		Set<Ingrediente> selectedIngredientes = (Set<Ingrediente>)getIngredientesField().getValue();
		
		menuItem.getIngredientes().clear();
		
		for(Ingrediente ingrediente: selectedIngredientes) {
			menuItem.getIngredientes().add(ingrediente);
		}
	}
	
	private void procesar() {
		
		if(Validar()) {
			if(getNombreTextField().isValid() &&
			   getPrecioTextField().isValid()) {
				if(menuItem != null) {
					menuItem.setNombre_menuItem(getNombreTextField().getValue());
					menuItem.setPrecio(new Double(getPrecioTextField().getValue()));
					menuItem.setImageName(imageUploader.file != null?imageUploader.file.getName():menuItem.getImageName());
					menuItem.setHay(getHayCheckBox().getValue()?1:0);
					cargarIngredientes(menuItem);				
					
					//System.out.println(menuItem.getNombre_menuItem()+"----"+menuItem.getMenu().getNombre_menu());
					
					MenuItem menuItemX = manageMenuItem.getMenuItem(menuItem.getNombre_menuItem());
					
					if(menuItemX != null) {
						manageMenuItem.updateMenuItem(menuItem);
					}
					
					close();
				} else {
						
					menuItem = manageMenuItem.getMenuItem(getNombreTextField().getValue(),menu.getNombre_menu());
					
					if(menuItem != null) {
						Notification.show("Ya existe un Item del Menu con Nombre = "+getNombreTextField().getValue(), Notification.Type.TRAY_NOTIFICATION);
						menuItem = null;
					} else {
						menuItem = new MenuItem();
						menuItem.setNombre_menuItem(getNombreTextField().getValue());
						menuItem.setPrecio(new Double(getPrecioTextField().getValue()));
						menuItem.setImageName(imageUploader.file != null?imageUploader.file.getName():menuItem.getImageName());
						menuItem.setMenu(menu);
						menuItem.setHay(getHayCheckBox().getValue()?1:0);
						cargarIngredientes(menuItem);
						menu.getItems().add(menuItem);
						close();
					}
				}
			}
		}
	}
}
