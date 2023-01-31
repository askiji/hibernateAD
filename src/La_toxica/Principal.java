package La_toxica;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import clases.Miembro;
import clases.Ministerio;
import utiles.SessionFactoryUtil;

public class Principal {
	public static void main(String[] args) {
		
		// conecto con hibernate
		SessionFactory sf = SessionFactoryUtil.getSessionFactory();
		Session s = sf.openSession();

		
		
		
		// ejercicio HQL _ Gobierno
//		ejercicio1(s);
//		ejercicio2(s , 2);
//		ejercicio3(s);
//		ejercicio3b(s, 0,4000.0);
//		ejercicio4(s,"J", "Logan",selecionarMinisterio(s,1));
//		ejercicio5(s,true);
//		ejercicio6(s);
//		ejercicio7(s);
//		ejercicio8(s);
//		ejercicio9(s);
		
		// realizar varias acciones
//			eje9(s, 200);
//			eje8(s , 90);
//			eje7(s , 50);
//			eje6(4, s);
//			eje3(s);
//			eje5(s);
//			eje4(s);
			s.close();
		
		
//		eje1(s, t);
	}

	private static void ejercicio9(Session s) {
		Scanner sc = new Scanner(System.in);
		String alias;
		ArrayList<String> listaNombre = new ArrayList<>();
		do {
			alias = sc.nextLine();
			if(alias.equals("FIN")) listaNombre.remove("FIN");
			listaNombre.add(alias);
		} while (!alias.equals("FIN"));
		for (String string : listaNombre) {
			Query q = s.createQuery("from Miembro where alias like :alias").setReadOnly(true).setMaxResults(1);
			q.setParameter("alias", string+"%");
			System.out.println(q.getSingleResult());
		}
		
	}

	private static void ejercicio8(Session s) {
		Query q = s.createQuery("from ministerio").setReadOnly(true);
	}

	private static void ejercicio7(Session s) {
		Query q = s.createQuery("from Miembro").setReadOnly(true);
		List<Miembro> miembros = q.getResultList();
		for (Miembro miembro : miembros) {
			if(miembro.getMinisterio() != null){
				System.out.println(miembro.toString()+" del ministerio "+ selecionarMinisterio(s,miembro.getMinisterio().getCodMinisterio()).getNombre());
			}
		}
	}

	private static void ejercicio6(Session s) {
		Query q = s.createQuery("from Ministerio where gastos > :media").setReadOnly(true);
		q.setParameter("media", ejercicio5(s,false));
		System.out.println(ejercicio5(s,false));
		List<Ministerio> ministerios = q.getResultList();
		System.out.println("Los minsiterio que gastan mas que la media son");
		for (Ministerio ministerio : ministerios) {
			System.out.println(ministerio.getNombre());
		}
	}

	private static double ejercicio5(Session s, boolean b) {
		Query q = s.createQuery("select avg(gastos)from Ministerio").setReadOnly(true);
//		List<Ministerio> m = q.getResultList();
		double media=(double) q.getSingleResult();
//		for (Ministerio ministerio : m) {
//			media += ministerio.getGastos();
//		}
		if(b)System.out.println("El gasto medio es " + media);
		
		return media;
	}

	private static Ministerio selecionarMinisterio(Session s,int i) {
		Query q = s.createQuery("from Ministerio where codMinisterio = :minID").setReadOnly(true).setMaxResults(1);
		q.setParameter("minID", i);
		return (Ministerio)q.getSingleResult();
	}

	private static void ejercicio4(Session s, String nombre,String apellido, Ministerio ministerio) {
		Query q=s.createQuery("from Miembro where ministerio = :ministerio and nombre like :nombre and apellido1 like :apellido").setReadOnly(true).setMaxResults(1);
		q.setParameter("ministerio", ministerio);
		q.setParameter("nombre", nombre+"%");
		q.setParameter("apellido", apellido);
		
		List<Miembro> miembros = q.getResultList();
		for (Miembro miembro : miembros) {
			System.out.println(miembro.toString());
		}
		
//		
//		System.out.println((Ministerio) q.getSingleResult());
				
	}

	private static void ejercicio3b(Session s, double d, double e) {
		Query q = s.createQuery("from Ministerio where presupuesto between :inicio  and :fin").setReadOnly(true);
		q.setParameter("inicio", d);
		q.setParameter("fin", e);
		List<Ministerio> ministerios = q.getResultList();
		for (Ministerio ministerio : ministerios) {
			System.out.println(ministerio.toString());
		}
		
	}

	private static void ejercicio3(Session s) {
		Query q = s.createQuery("from Ministerio where presupuesto > 300000 order by presupuesto asc").setReadOnly(true).setMaxResults(2);
		List<Ministerio> ministerios = q.getResultList();
		for (Ministerio ministero : ministerios) {
			System.out.println("ministerio del ejercico "+ ministero.getNombre());
		}
		
	}

	private static void ejercicio2(Session s, int posicion) {
		Query q = s.createQuery("from Ministerio").setReadOnly(true);
		q.setFirstResult(posicion).setMaxResults(1);
		Ministerio m = (Ministerio) q.getSingleResult();
		System.out.println("El ministerio que ocupa la posicion "+ posicion+ " es "+ m.getNombre());
		
	}

	private static void ejercicio1(Session s) {
		double presupuesto=0;
		double gasto=0;
		String nombreMinisterio="";
		Query q = s.createQuery("from Ministerio").setReadOnly(true);
		List<Ministerio> ministerios = q.getResultList();
		for (Ministerio ministerio : ministerios) {
			presupuesto = ministerio.getPresupuesto();
			gasto = ministerio.getGastos();
			nombreMinisterio = ministerio.getNombre();
			System.out.println("El "+nombreMinisterio + " tiene un presupeusto de "+ presupuesto+"€ y unos gastos de "+ gasto +" €" );
		}
	}

	private static void eje9(Session s, int i) {
		Transaction t = s.beginTransaction();
		Ministerio m = new Ministerio();
		try {
			s.load(m,i);
			double presuIni= m.getPresupuesto();
			m.setPresupuesto(presuIni*1.1);
			t.commit();
			System.out.println("El presupuesto del Ministerio de Igualdad pasa de "+presuIni+" € a "+m.getPresupuesto()+" €");
		} catch (Exception e) {
			System.out.println("No se ha podido subir el presupuesto");
//			e.printStackTrace();
		}
	}

	private static void eje8(Session s, int i) {
		Transaction t = s.beginTransaction();
		Miembro m1 = new Miembro();
		try {
			s.load(m1, i);
			s.delete(m1);
			System.out.println("Se ha eliminado el miembro");
			// importante comitear porque es modificar la base de datos
			t.commit();
		} catch (Exception e) {
			System.out.println("No se ha podido eliminar el miembro");
			t.rollback();
//			e.printStackTrace();
		}
	}

	private static void eje7(Session s, int i) {
		Transaction t = s.beginTransaction();
		try {
			Ministerio m = new Ministerio();
			s.load(m, i);
			System.out.println(m.toString());
		} catch (Exception e) {
			System.out.println("No ha ministerio");
			t.rollback();
			// TODO: handle exception
		}
	}

	private static void eje6(int cod, Session s) {
		Ministerio m1 = new Ministerio();
		Transaction t = s.beginTransaction();
		try {
			s.load(m1, cod);
			int idMinisterio;
			String nombre = m1.getNombre();
			double presupuesto = m1.getPresupuesto();
			double gastos = m1.getGastos();
				System.out.println("El ministerio "+ cod +" es el Ministerio de "+nombre+" tiene un presupuesto de "+presupuesto+" y\n"
						+ "unos gastos de "+ gastos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			t.rollback();
			System.out.println("No ha ministerio");
//			e.printStackTrace();
		}
			
		
	}

	private static void eje5(Session s) {
		Ministerio m1 = new Ministerio("Estetica", 2000.2, 1900);
		int idMinisterio =(Integer) s.save(m1);
		
		Miembro persona1 = new Miembro(m1, "0000a", "fiuu", "fiuuuu", "El robos");
		int id = (Integer)s.save(persona1);
		System.out.println("Se ha insertado el miembro con ID="+ id+ " en el ministerio: " + idMinisterio);
	}

	private static void eje4(Session s) {
		System.out.println("Aqui vamos a hacer cositas");
		Ministerio m1 = new Ministerio();
		s.load(m1, 4);
		s.save(new Miembro(m1,"000h", "lobo", "paqueiras","Lobinsky"));
	}

	private static void eje3(Session s) {
		Ministerio m = new Ministerio();
		s.load(m, 3);
		System.out.println(m.toString());
	}

	private static void eje1(Session s, Transaction t) {
		Ministerio m1 = new Ministerio("Toxicos", 2000.2, 1900);
		Miembro persona1 = new Miembro(m1, "000a", "Pepito", "Palotes", "El estafas");
		s.save(m1);
		s.save(persona1);
		t.commit();
		s.close();
	}
}
