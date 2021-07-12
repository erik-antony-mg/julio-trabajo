package pe.com.fabrica.aldesa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.com.fabrica.aldesa.dto.MenuContext;
import pe.com.fabrica.aldesa.dto.SubmenuContext;
import pe.com.fabrica.aldesa.beans.Rol;
import pe.com.fabrica.aldesa.beans.SubMenu;
import pe.com.fabrica.aldesa.beans.Usuario;
import pe.com.fabrica.aldesa.security.auth.web.AuthUserData;

public class WebUtil {

	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

	private WebUtil() {
		throw new IllegalStateException();
	}

	/**
	 * Este metodo crea un objeto que contiene los datos que enviarán junto con el
	 * token en la respuesta Http
	 *
	 */
	public static AuthUserData getAuthUser(Usuario usuario) {
		AuthUserData authUser = new AuthUserData();
		authUser.setId(usuario.getIdPersona());
		authUser.setName(usuario.getNombres());
		authUser.setLastname(usuario.getApellidoPaterno());
		authUser.setUsername(usuario.getUsername());
		authUser.setEmail(usuario.getEmail());
		authUser.setImagen(usuario.getImagen());
		authUser.setActivo(usuario.getActivo());

		Rol roltmp = usuario.getRol();
		authUser.setRol(roltmp.getNombre());

		Set<SubMenu> subMenus = roltmp.getSubMenus();

		logger.debug("Total de subMenus: {}", subMenus.size());

		List<SubmenuContext> smContexts = new ArrayList<>();

		for (SubMenu sm : subMenus) {
			logger.debug("{}", sm);
			SubmenuContext smContext = new SubmenuContext();
			smContext.setId(sm.getSubmenuId());
			smContext.setNombre(sm.getNombre());
			smContext.setOrden(sm.getNumeroOrden());
			smContext.setRuta(sm.getRuta());

			MenuContext mContext = new MenuContext();
			mContext.setId(sm.getMenu().getIdMenu());
			mContext.setNombre(sm.getMenu().getNombre());
			mContext.setOrden(sm.getMenu().getNumeroOrden());
			mContext.setIcono(sm.getMenu().getIcono());

			smContext.setMenu(mContext);
			smContexts.add(smContext);
		}
		authUser.setSubmenus(smContexts);

		return authUser;
	}

}
