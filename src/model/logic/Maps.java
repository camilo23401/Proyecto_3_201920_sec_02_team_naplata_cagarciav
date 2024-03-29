package model.logic;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.sun.prism.paint.Color;
import com.teamdev.jxmaps.Circle;
import com.teamdev.jxmaps.CircleOptions;
import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.InfoWindowOptions;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNoDirigido;

public class Maps extends MapView {

	// Objeto Google Maps
	private Map map;
	private MVCModelo modelo;

	//Coordenadas del camino a mostrar (secuencia de localizaciones (Lat, Long)) //ACA DEBEN IR LOS VERTICEs
	public Maps(ArregloDinamico<Coordenadas> pArreglo, GrafoNoDirigido<Integer, Coordenadas> pSubGrafo,ArregloDinamico<Interseccion>arcos)
	{	
		setOnMapReadyHandler( new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus status)
			{
				ArregloDinamico<Coordenadas> aux = pArreglo;
				LatLng[] rta = new LatLng[aux.darTamano()];
				for(int i=0; i< aux.darTamano();i++)
				{
					Coordenadas actual = aux.darElementoPos(i);
					rta[i] = new LatLng(actual.darLatitud(), actual.darLongitud());
				}

				if ( status == MapStatus.MAP_STATUS_OK )
				{
					map = getMap();

					// Configuracion de localizaciones intermedias del path (circulos)
					CircleOptions middleLocOpt= new CircleOptions(); 
					middleLocOpt.setFillColor("#00FF00");  // color de relleno
					middleLocOpt.setFillOpacity(0.5);
					middleLocOpt.setStrokeWeight(1.0);

					for(int i=0; i<rta.length;i++)
					{
						Circle middleLoc1 = new Circle(map);
						middleLoc1.setOptions(middleLocOpt);
						middleLoc1.setCenter(rta[i]); 
						middleLoc1.setRadius(20); //Radio del circulo
					}

					//Configuracion de la linea del camino
					PolylineOptions pathOpt = new PolylineOptions();
					pathOpt.setStrokeColor("#000000");	  // color de linea	
					pathOpt.setStrokeOpacity(4);
					pathOpt.setStrokeWeight(2);
					pathOpt.setGeodesic(false);
					Polyline linea;
					for (int i=0;i<arcos.darTamano();i++)
					{
						Interseccion arco=arcos.darElementoPos(i);
						linea = new Polyline(map); 														
						linea.setOptions(pathOpt); 
						LatLng[] coordenadas = {new LatLng(arco.getLatin(), arco.getLonin()), new LatLng(arco.getLatin1(), arco.getLonin2())};
						linea.setPath(coordenadas);
					}
					System.out.println("carga correcta de datos en el mapa");
					initMap(map);

				}
			}
		}
				);
	}

	public Maps(ArregloDinamico<Coordenadas>  pArreglo)
	{	
		setOnMapReadyHandler( new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus status)
			{

				ArregloDinamico<Coordenadas> aux = pArreglo;
				LatLng[] rta = new LatLng[aux.darTamano()];
				for(int i=0; i< aux.darTamano();i++)
				{
					Coordenadas actual = aux.darElementoPos(i);
					rta[i] = new LatLng(actual.darLatitud(), actual.darLongitud());
				}

				if ( status == MapStatus.MAP_STATUS_OK )
				{
					map = getMap();


					// Configuracion de localizaciones intermedias del path (circulos)
					CircleOptions middleLocOpt= new CircleOptions(); 
					middleLocOpt.setFillColor("#00FF00");  // color de relleno
					middleLocOpt.setFillOpacity(0.5);
					middleLocOpt.setStrokeWeight(1.0);

					for(int i=0; i<rta.length;i++)
					{
						Circle middleLoc1 = new Circle(map);
						middleLoc1.setOptions(middleLocOpt);
						middleLoc1.setCenter(rta[i]); 
						middleLoc1.setRadius(50); //Radio del circulo
					}

					map.setCenter(rta[0]);
					map.setZoom(15.0);

					//Configuracion de la linea del camino
					PolylineOptions pathOpt = new PolylineOptions();
					pathOpt.setStrokeColor("#000000");	  // color de linea	
					pathOpt.setStrokeOpacity(4);
					pathOpt.setStrokeWeight(2);
					pathOpt.setGeodesic(false);
					Polyline linea;
					
					System.out.println("carga correcta de datos en el mapa camino");
					initMap(map);

				}
			}
		}
				);
	}
	public Maps(LatLng inicio,LatLng fin,ArregloDinamico<Interseccion>arcos)
	{	
		setOnMapReadyHandler( new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus status)
			{


				if ( status == MapStatus.MAP_STATUS_OK )
				{
					map = getMap();

					// Configuracion de localizaciones intermedias del path (circulos)
					CircleOptions middleLocOpt= new CircleOptions(); 
					middleLocOpt.setFillColor("#00FF00");  // color de relleno
					middleLocOpt.setFillOpacity(0.5);
					middleLocOpt.setStrokeWeight(1.0);



					Circle middleLoc1 = new Circle(map);
					middleLoc1.setOptions(middleLocOpt);
					middleLoc1.setCenter(inicio); 
					middleLoc1.setRadius(20); //Radio del circulo

					Circle middleLoc2 = new Circle(map);
					middleLoc2.setOptions(middleLocOpt);
					middleLoc2.setCenter(fin); 
					middleLoc2.setRadius(20); //Radio del circulo

					map.setCenter(inicio);
					map.setZoom(15.0);


					//Configuracion de la linea del camino
					PolylineOptions pathOpt = new PolylineOptions();
					pathOpt.setStrokeColor("#000000");	  // color de linea	
					pathOpt.setStrokeOpacity(4);
					pathOpt.setStrokeWeight(2);
					pathOpt.setGeodesic(false);
					Polyline linea;
					for (int i=0;i<arcos.darTamano();i++)
					{
						Interseccion arco=arcos.darElementoPos(i);
						linea = new Polyline(map); 														
						linea.setOptions(pathOpt);
						System.out.println("here");
						System.out.println(arco.getLatin()+"."+arco.getLonin());
						System.out.println(arco.getLatin1()+"."+arco.getLonin2());
						LatLng[] coordenadas = {new LatLng(arco.getLatin(),arco.getLonin()), new LatLng(arco.getLatin1(),arco.getLonin2())};
						linea.setPath(coordenadas);

					}
					System.out.println("carga correcta de datos en el mapa camino");
					initMap(map);

				}
			}
		}
				);
	}
	public Maps(ArregloDinamico<Coordenadas>cor,int n)
	{	
		setOnMapReadyHandler( new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus status)
			{
				ArregloDinamico<Coordenadas> aux = cor;
				LatLng[] rta = new LatLng[aux.darTamano()];
				for(int i=0; i< aux.darTamano();i++)
				{
					Coordenadas actual = aux.darElementoPos(i);
					rta[i] = new LatLng(actual.darLatitud(), actual.darLongitud());
				}


				if ( status == MapStatus.MAP_STATUS_OK )
				{
					map = getMap();


					// Configuracion de localizaciones intermedias del path (circulos)
					CircleOptions middleLocOpt= new CircleOptions(); 
					middleLocOpt.setFillColor("#00FF00");  // color de relleno
					middleLocOpt.setFillOpacity(0.5);
					middleLocOpt.setStrokeWeight(1.0);

					for(int i=0; i<rta.length;i++)
					{
						Circle middleLoc1 = new Circle(map);
						middleLoc1.setOptions(middleLocOpt);
						middleLoc1.setCenter(rta[i]); 
						middleLoc1.setRadius(30); //Radio del circulo
					}

					map.setCenter(rta[0]);
					map.setZoom(15.0);

					//Configuracion de la linea del camino
					PolylineOptions pathOpt = new PolylineOptions();
					pathOpt.setStrokeColor("#000000");	  // color de linea	
					pathOpt.setStrokeOpacity(4);
					pathOpt.setStrokeWeight(2);
					pathOpt.setGeodesic(false);
					Polyline linea;
					/*
					for (int i=0;i<arcos.darTamano();i++)
					{
						Interseccion arco=arcos.darElementoPos(i);
						linea = new Polyline(map); 														
						linea.setOptions(pathOpt);
						System.out.println("here");
						System.out.println(arco.getLatin()+"."+arco.getLonin());
						System.out.println(arco.getLatin1()+"."+arco.getLonin2());
						LatLng[] coordenadas = {new LatLng(arco.getLatin(),arco.getLonin()), new LatLng(arco.getLatin1(),arco.getLonin2())};
						linea.setPath(coordenadas);

					}
					 */
					initMap(map);
					System.out.println("carga correcta de datos en el mapa camino");


				}
			}
		}
				);
	}
	public void recuperarCoordenadasVertices(ArregloDinamico<Coordenadas> pArreglo)
	{
		ArregloDinamico<Coordenadas> aux = pArreglo;
		LatLng[] rta = new LatLng[aux.darTamano()];
		for(int i=0; i< aux.darTamano();i++)
		{
			Coordenadas actual = aux.darElementoPos(i);
			rta[i] = new LatLng(actual.darLatitud(), actual.darLongitud());
		}

		updateUI();
	}

	public void initMap(Map map)
	{
		MapOptions mapOptions = new MapOptions();
		MapTypeControlOptions controlOptions = new MapTypeControlOptions();
		controlOptions.setPosition(ControlPosition.BOTTOM_LEFT);
		mapOptions.setMapTypeControlOptions(controlOptions);

		map.setOptions(mapOptions);

	}

	public void initFrame(String titulo)
	{
		JFrame frame = new JFrame(titulo);
		frame.setSize(800, 800);
		frame.add(this, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}