package org.mule.miw.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Reader {

	public Properties read(String location) {

		String archivoConfiguracion = location;
		Properties propiedades = new Properties();

		try (FileInputStream fis = new FileInputStream(archivoConfiguracion);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {

			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split("=");
				if (partes.length == 2) {
					String nombrePropiedad = partes[0].trim();
					String valorPropiedad = partes[1].trim();
					propiedades.setProperty(nombrePropiedad, valorPropiedad);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propiedades;
	}

	public Properties readLegendaries(String location) {

		String archivoConfiguracion = location;
		Properties propiedades = new Properties();

		try (FileInputStream fis = new FileInputStream(archivoConfiguracion);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {

			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split("=");
				String nombrePropiedad = partes[0].trim();
				String[] types = partes[1].split(",");
				for (String type : types) {
					propiedades.setProperty(nombrePropiedad, type.trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propiedades;
	}

}
