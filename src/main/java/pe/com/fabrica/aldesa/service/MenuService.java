package pe.com.fabrica.aldesa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Menu;
import pe.com.fabrica.aldesa.beans.SubMenu;
import pe.com.fabrica.aldesa.repository.MenuRepository;
import pe.com.fabrica.aldesa.dto.MenuContext;
import pe.com.fabrica.aldesa.dto.SubmenuContext;

import java.util.ArrayList;

@Service
public class MenuService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuRepository menuRepository;

	public ApiResponse findAll() {
		List<Menu> menus = menuRepository.findAll();
		int total = menus.size();
		logger.debug("Total Menus: {}", total);
		List<MenuContext> listMContext = new ArrayList<>();
		for (Menu m : menus) {
			logger.debug("{}", m);
			MenuContext mContext = new MenuContext();
			mContext.setId(m.getIdMenu());
			mContext.setNombre(m.getNombre());
			mContext.setOrden(m.getNumeroOrden());
			mContext.setIcono(m.getIcono());

			List<SubmenuContext> listSmContext = new ArrayList<>();
			for (SubMenu sm : m.getSubmenus()) {
				SubmenuContext smContext = new SubmenuContext();
				smContext.setId(sm.getSubmenuId());
				smContext.setNombre(sm.getNombre());
				smContext.setOrden(sm.getNumeroOrden());
				smContext.setRuta(sm.getRuta());
				listSmContext.add(smContext);
			}

			mContext.setSubmenu(listSmContext);
			listMContext.add(mContext);
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listMContext, total);
	}

}
