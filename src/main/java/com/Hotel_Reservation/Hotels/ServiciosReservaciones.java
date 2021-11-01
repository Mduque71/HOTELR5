/*
 * Paquete
 */
package com.Hotel_Reservation.Hotels;


/*
Importaciones
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco Duque
 */
@Service
public class ServiciosReservaciones {
    @Autowired
    private RepositorioReservaciones metodosCrud;
    
    /*
    * Servicio de metodo get para consultar las reservaciones 
    */
    public List<Reservaciones> getAll(){
        return metodosCrud.getAll();
    }
    
    /*
    * Servicio get para la consulta de una reservación por id
    */
    public Optional<Reservaciones> getReservation(int reservationId) {
        return metodosCrud.getReservation(reservationId);
    }
    
     /*
    * Servicio para crear una reservación
    */
    public Reservaciones save(Reservaciones reservation){
        if(reservation.getIdReservation()==null){
            return metodosCrud.save(reservation);
        }else{
            Optional<Reservaciones> e= metodosCrud.getReservation(reservation.getIdReservation());
            if(e.isEmpty()){
                return metodosCrud.save(reservation);
            }else{
                return reservation;
            }
        }
    }
    
    /*
    * Servicio para actualiar una reservación
    */
    public Reservaciones update(Reservaciones reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservaciones> e= metodosCrud.getReservation(reservation.getIdReservation());
            if(!e.isEmpty()){

                if(reservation.getStartDate()!=null){
                    e.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    e.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    e.get().setStatus(reservation.getStatus());
                }
                metodosCrud.save(e.get());
                return e.get();
            }else{
                return reservation;
            }
        }else{
            return reservation;
        }
    }
    
    /*
    * Servicio para eliminar una reservación
    */
    public boolean deleteReservation(int reservationId) {
        Boolean aBoolean = getReservation(reservationId).map(reservation -> {
            metodosCrud.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    /*
    * Servicio que genera Json con reporte de Cantidad de reservas completas vs canceladas.
    */
    public StatusReservas getReporteStatusReservas () {
        List <Reservaciones>completed = metodosCrud.ReservacionStatus("completed");
        List <Reservaciones>cancelled = metodosCrud.ReservacionStatus("cancelled");
        return new StatusReservas(completed.size(), cancelled.size());
    }
    
    /*
    * Servicio que genera Json con reporte Cantidad de reservas en un tiempo determinado.
    */
    public List<Reservaciones> getReportTiempoReservas(String datoA, String datoB){
        SimpleDateFormat parser=new SimpleDateFormat ("yyyy-MM-dd");
        Date datoUno = new Date();
        Date datoDos = new Date();
        
        try{
            datoUno = parser.parse(datoA);
            datoDos = parser.parse(datoB);  
        }catch(ParseException evt){
            evt.printStackTrace();
        }if(datoUno.before(datoDos)){
            return metodosCrud.ReservacionTiempo(datoUno, datoDos);
        
        }else{
            return new ArrayList<>();
        }
        
    }
    
    /*
    * Servicio que genera Json con reporte Top de los clientes que más dinero le han dejado a la compañía.
    */
    public List<ContadorClientes> servicioTopClientes(){
        return metodosCrud.getTopClientes();
    }
    
    
    
}
