/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import IOCommunication.PatientServerCommunication;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noeli
 */

public class Bitalino implements Serializable{
     private static final long serialVersionUID = 123456000L;
     
    private Integer id;
    private Date date;
    private SignalType signal_type;
    private final Float duration=60.0F;//seconds-> 1 min of recording
    private String signalValues;
    private Report report;
     
    public Bitalino(){
    super();
    }
     
    public Bitalino (Date date,SignalType signal_type){
         this.date=date;
         this.signal_type=signal_type;
     }

    public Bitalino(Integer id, Date date, SignalType signal_type, String signalValues, Report report) {
        this.id = id;
        this.date = date;
        this.signal_type = signal_type;
        this.signalValues = signalValues;
        this.report = report;
    }
    
    
    
    /**
     * Method used to store the signals in a list
     * @param bitalino
     * @param signal_type
     * @return list with all the frames that have been recorded
     */
    public List<Frame> storeRecordedSignals(BITalino bitalino, SignalType signal_type) {
        List<Frame> signalFrames = new ArrayList<>();
        try {
            int samplingRate = 100; // Example sampling rate in Hz
            int channel = 0;
            
            if(signal_type==SignalType.ECG){
                channel=1;
            }else{
                channel=2;
            }
            int [] channels={channel};
            
            bitalino.start(channels);

            int samplesToRead = (int) (duration * samplingRate);

            for (int i = 0; i < samplesToRead; i++) {
                try {
                    Frame[] frames = bitalino.read(1); // 1 sample at a time
                    signalFrames.add(frames[0]); 
                } catch (BITalinoException ex) {
                    Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            bitalino.stop(); // Stop acquisition
        } catch (BITalinoException ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signalFrames;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public SignalType getSignal_type() {
        return signal_type;
    }

    public Float getDuration() {
        return duration;
    }

    public Report getReport() {
        return report;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSignal_type(SignalType signal_type) {
        this.signal_type = signal_type;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    /**
     * setSignalValues
     * Transforms the list of frames saved from the recording into a string in order to save it in the database
     * @param frames list of frames recorded
     */
    public void setSignalValues(List<Frame> frames, int channelIndex) {
        StringBuilder sb = new StringBuilder();
        Iterator<Frame> it = frames.iterator();
        while (it.hasNext()) {
            Frame frame = it.next();
            sb.append(frame.analog[channelIndex]);
            sb.append("\n");
        }
        this.signalValues = sb.toString();;
    }

    
    @Override
    public String toString() {
        return "Bitalino{" + "id=" + id + ", date=" + date + ", signal_type=" + signal_type + ", duration=" + duration + ", report=" + report + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.date);
        hash = 79 * hash + Objects.hashCode(this.signal_type);
        hash = 79 * hash + Objects.hashCode(this.duration);
        hash = 79 * hash + Objects.hashCode(this.report);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bitalino other = (Bitalino) obj;
        if (!Objects.equals(this.signal_type, other.signal_type)) {
            return false;
        }
       
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        return Objects.equals(this.report, other.report);
    }
     
         
     
}


     
