package com.springboot.print.app.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	
	private String url;
	private Page<T> page;
	
	public String getUrl() {
		return url;
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public int getPaginaActual() {
		return paginaActual;
	}
	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	
	public boolean isFirts() {
		return page.isFirst();
		
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevius() {
		return page.hasPrevious();
	}
	private int totalPaginas;
	private int numeroElementosPorPaginas;
	private int paginaActual;
	private List<PageItem> paginas;
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		numeroElementosPorPaginas = page.getSize();
		
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;
		
		int desde, hasta;
		if(totalPaginas<= numeroElementosPorPaginas) {
			desde = 1;
			hasta = totalPaginas;			
		}
		else {
			if(paginaActual<= numeroElementosPorPaginas) {
				desde = 1;
				hasta = numeroElementosPorPaginas;				
			}
			else if(paginaActual >= totalPaginas - numeroElementosPorPaginas/2) {
				desde = totalPaginas - numeroElementosPorPaginas +1;
				hasta = numeroElementosPorPaginas;				
			}
			else {
				desde = paginaActual - numeroElementosPorPaginas /2;
				hasta = numeroElementosPorPaginas;
			}
		}
		
		for (int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde +i ));
			
		}
		
	}

}
