package pe.com.fabrica.aldesa.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.dto.MenuContext;
import pe.com.fabrica.aldesa.dto.RolContext;
import pe.com.fabrica.aldesa.dto.SubmenuContext;
import pe.com.fabrica.aldesa.beans.Rol;
import pe.com.fabrica.aldesa.beans.SubMenu;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.RolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class RolService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RolRepository rolRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Rol> rolesPage = rolRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, rolesPage.getTotalPages());

		if (rolesPage.getContent().isEmpty()) {
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Collections.emptyList(),
					Math.toIntExact(rolesPage.getTotalElements()));
		}

		List<RolContext> listRolContext = new ArrayList<>();
		for (Rol r : rolesPage.getContent()) {
			logger.debug("{}", r);

			List<SubmenuContext> listSmContext = new ArrayList<>();
			Set<SubMenu> subMenus = r.getSubMenus();
			for (SubMenu sm : subMenus) {
				logger.debug("{}", sm);

				SubmenuContext smContext = new SubmenuContext();
				smContext.setId(sm.getSubmenuId());
				smContext.setNombre(sm.getNombre());
				smContext.setOrden(sm.getNumeroOrden());

				MenuContext mContext = new MenuContext();
				mContext.setId(sm.getMenu().getIdMenu());
				mContext.setNombre(sm.getMenu().getNombre());
				mContext.setOrden(sm.getMenu().getNumeroOrden());
				mContext.setIcono(sm.getMenu().getIcono());

				smContext.setMenu(mContext);

				listSmContext.add(smContext);
			}

			RolContext rolContext = new RolContext();
			rolContext.setId(r.getIdRol());
			rolContext.setNombre(r.getNombre());
			rolContext.setSubmenus(listSmContext);

			listRolContext.add(rolContext);
		}

		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listRolContext,
				Math.toIntExact(rolesPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Rol tmpRol = rolRepository.findById(id).orElse(null);
		logger.debug("Rol: {}", tmpRol);

		if (null == tmpRol) {
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpRol);
		}

		RolContext rolContext = new RolContext();
		rolContext.setId(tmpRol.getIdRol());
		rolContext.setNombre(tmpRol.getNombre());

		List<SubmenuContext> listSmContext = new ArrayList<>();

		Set<SubMenu> subMenus = tmpRol.getSubMenus();

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
			listSmContext.add(smContext);
		}

		rolContext.setSubmenus(listSmContext);

		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), rolContext);
	}

	public ApiResponse save(RolContext rolContext) throws ApiException {

		Rol rol = new Rol();
		rol.setNombre(rolContext.getNombre().toUpperCase());

		List<SubMenu> sList = new ArrayList<>();

		for (SubmenuContext smContext : rolContext.getSubmenus()) {
			SubMenu subMenu = new SubMenu();
			subMenu.setSubmenuId(smContext.getId());
			subMenu.setNombre(smContext.getNombre());
			subMenu.setNumeroOrden(smContext.getOrden());
			subMenu.setRuta(smContext.getRuta());
			sList.add(subMenu);
		}

		rol.setSubMenus(sList.stream().collect(Collectors.toSet()));

		Rol responseRol;

		try {
			responseRol = rolRepository.save(rol);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseRol);
	}

	public ApiResponse update(int id, RolContext rolContext) throws ApiException {
		Rol tmpRol = rolRepository.findById(id).orElse(null);

		logger.debug("Rol: {}", tmpRol);

		if (null == tmpRol) {
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpRol);
		}

		Rol rol = rolRepository.findById(id).orElse(null);
		rol.setNombre(rolContext.getNombre().toUpperCase());

		List<SubMenu> sList = new ArrayList<>();

		for (SubmenuContext smContext : rolContext.getSubmenus()) {
			SubMenu subMenu = new SubMenu();
			subMenu.setSubmenuId(smContext.getId());
			subMenu.setNombre(smContext.getNombre());
			subMenu.setNumeroOrden(smContext.getOrden());
			subMenu.setRuta(smContext.getRuta());
			sList.add(subMenu);
		}

		rol.setSubMenus(sList.stream().collect(Collectors.toSet()));

		Rol responseRol;

		try {
			responseRol = rolRepository.save(rol);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseRol);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Rol tmpRol = rolRepository.findById(id).orElse(null);
		logger.debug("Rol: {}", tmpRol);
		if (null != tmpRol) {
			rolRepository.deleteById(id);
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Rol " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByNameContaining(String nombre, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Rol> rolesPage = rolRepository.findByNombreContaining(nombre, pageable);
		logger.debug("Página {} de: {}", pageNumber, rolesPage.getTotalPages());

		if (rolesPage.getContent().isEmpty()) {
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Collections.emptyList(),
					Math.toIntExact(rolesPage.getTotalElements()));
		}

		List<RolContext> listRolContext = new ArrayList<>();
		for (Rol r : rolesPage.getContent()) {
			logger.debug("{}", r);
			
			List<SubmenuContext> listSmContext = new ArrayList<>();
			Set<SubMenu> subMenus = r.getSubMenus();
			for (SubMenu sm : subMenus) {
				logger.debug("{}", sm);

				SubmenuContext smContext = new SubmenuContext();
				smContext.setId(sm.getSubmenuId());
				smContext.setNombre(sm.getNombre());
				smContext.setOrden(sm.getNumeroOrden());

				MenuContext mContext = new MenuContext();
				mContext.setId(sm.getMenu().getIdMenu());
				mContext.setNombre(sm.getMenu().getNombre());
				mContext.setOrden(sm.getMenu().getNumeroOrden());
				mContext.setIcono(sm.getMenu().getIcono());

				smContext.setMenu(mContext);

				listSmContext.add(smContext);
			}

			RolContext rolContext = new RolContext();
			rolContext.setId(r.getIdRol());
			rolContext.setNombre(r.getNombre());
			rolContext.setSubmenus(listSmContext);

			listRolContext.add(rolContext);

		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listRolContext,
				Math.toIntExact(rolesPage.getTotalElements()));
	}

}
