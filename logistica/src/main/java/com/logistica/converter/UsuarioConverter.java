package com.logistica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.logistica.dao.UsuarioDAO;
import com.logistica.model.Usuario;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {
	
	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			System.out.println("ID USUARIO NO CONVERTER = "+value);
			Long id = new Long(value);
			retorno = usuarioDAO.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}

		return "";
	}

}
