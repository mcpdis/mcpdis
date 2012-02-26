/*
 * PatientDao.java
 *
 * Created on March 12, 2006, 1:02 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;

import com.sun.midp.Configuration;
import javax.microedition.rms.*;
import java.io.*;
/**
 *
 * @author user
 */
public class PatientDao {
    private RecordStore recordstore = null;
    private RecordEnumeration recordEnumeration = null;
    
    /** Creates a new instance of PatientDao */
    public PatientDao() {
    }

    public boolean hasRecords() {
        return numRecords() > 0;
    }
    
    public int numRecords() {
        int count;
        StartRecordOperations();
        try {
            count = recordstore.getNumRecords();
        } catch (Exception e) {
            count = 0;
        }
        EndRecordOperations();
        return count;
    }
    
    public int save(Patient patient) {
        StartRecordOperations();
    
        int returnValue;
        try {
            byte[] outputRecord;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream outputDataStream = new DataOutputStream(outputStream);
            
            outputDataStream.writeUTF(patient.firstName);
            outputDataStream.writeUTF(patient.lastName);
            outputDataStream.writeBoolean(patient.gender);
            outputDataStream.writeInt(patient.getDateOfBirthInSeconds());
            outputDataStream.flush();
            outputRecord = outputStream.toByteArray();
            
            if (patient.id != -1) {
                recordstore.setRecord(patient.id, outputRecord, 0, outputRecord.length);
            } else {
                patient.id = recordstore.addRecord(outputRecord, 0, outputRecord.length);
            }
            outputStream.reset();
            outputStream.close();
            outputDataStream.close();
            returnValue = patient.id;
        } catch (Exception error) {
            returnValue = -1;
        }
        EndRecordOperations();
        return returnValue;
    }
    
    public Patient find(int patientId) {
        StartRecordOperations();
        ByteArrayInputStream stream;
        DataInputStream reader;
        Patient p;
        try {
            byte[] rec = new byte[recordstore.getRecordSize(patientId)];
            rec = recordstore.getRecord(patientId);
            stream = new ByteArrayInputStream(rec);
            reader = new DataInputStream(stream);
            p = new Patient();
            p.id = patientId;
            p.firstName = reader.readUTF();
            p.lastName = reader.readUTF();
            p.gender = reader.readBoolean();
            int dateOfBirth = reader.readInt();
            p.setDateOfBirthInSeconds(dateOfBirth);
            stream.close();
        } catch (Exception e) {
            p = null;
        }

        EndRecordOperations();
        return p;
    }
    
    public Patient[] findAll() {
        StartRecordOperations();
        Patient[] ret;
        try {
            ret = new Patient[recordstore.getNumRecords()];    
        } catch (Exception e) {
            return new Patient[0];
        }

        ByteArrayInputStream stream;
        DataInputStream reader;

        try {
            recordEnumeration = recordstore.enumerateRecords(
               null, new PatientOrderByName(), false
            );
            int counter = 0;
            while (recordEnumeration.hasNextElement()) {
                int i = recordEnumeration.nextRecordId();
                byte[] rec = new byte[recordstore.getRecordSize(i)];
                rec = recordstore.getRecord(i);
                stream = new ByteArrayInputStream(rec);
                reader = new DataInputStream(stream);

                Patient p = new Patient();
                p.id = i;
                p.firstName = reader.readUTF();
                p.lastName = reader.readUTF();
                p.gender = reader.readBoolean();
                p.setDateOfBirthInSeconds(reader.readInt());
                ret[counter++] = p;
                stream.close();
            }
            
        } catch (Exception error) {
            recordEnumeration.destroy();
        }
        
        EndRecordOperations();
        return ret;
    }
    
    public boolean delete(int patientId) {
        boolean ret;
        StartRecordOperations();
        try {
            recordstore.deleteRecord(patientId);
            FormulaDao formulaDao = new FormulaDao();
            FormulaValue[] fvs = formulaDao.findByPatientId(patientId);
            for (int i = 0; i < fvs.length; i++) {
                formulaDao.delete(fvs[i].id);
            }
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        EndRecordOperations();
        return true;
    }
    
    public String serialize(Patient patient) {
        StringBuffer ret = new StringBuffer();
        ret.append("id=" + patient.id);
        ret.append("&firstName=" + RemoteHelper.urlEncode(patient.firstName));
        ret.append("&lastName=" + RemoteHelper.urlEncode(patient.lastName));
        ret.append("&gender=" + patient.getGenderInWords());
        ret.append("&dateOfBirth=" + String.valueOf(patient.getDateOfBirthInSeconds()));
        ret.append("&userEmail=" + RemoteHelper.getUserEmail());
        ret.append("&hashCode=" + RemoteHelper.getHashcode());
        return ret.toString();
    }
    
    private void StartRecordOperations() {
        try {
            //RecordStore.deleteRecordStore("MCPDIS_Patient_DB");
            recordstore = RecordStore.openRecordStore("MCPDIS_Patient_DB", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void EndRecordOperations() {
        try {
            //recordstore.deleteRecordStore("MCPDIS_Patient_DB");
            recordstore.closeRecordStore();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/* Comparator for patients */
class PatientOrderByName implements RecordComparator {
    private ByteArrayInputStream comparatorInputStream = null;
    private DataInputStream comparatorInputDataType = null;
    
    public int compare(byte[] record1, byte[] record2) {
        String firstname1, lastname1;
        String firstname2, lastname2;
        
        try {
            comparatorInputStream = new ByteArrayInputStream(record1);
            comparatorInputDataType = new DataInputStream(comparatorInputStream);
            firstname1 = comparatorInputDataType.readUTF();
            lastname1 = comparatorInputDataType.readUTF();
            
            comparatorInputStream = new ByteArrayInputStream(record2);
            comparatorInputDataType = new DataInputStream(comparatorInputStream);
            firstname2 = comparatorInputDataType.readUTF();
            lastname2 = comparatorInputDataType.readUTF();

            int lastNameResult = lastname1.compareTo(lastname2);
            int firstNameResult = firstname1.compareTo(firstname2);
            
            if (lastNameResult == 0) {
                if (firstNameResult == 0) {
                    return RecordComparator.EQUIVALENT;
                } else if (firstNameResult < 0) {
                    return RecordComparator.PRECEDES;
                } else {
                    return RecordComparator.FOLLOWS;
                }
            } else if (lastNameResult < 0) {
                return RecordComparator.PRECEDES;
            } else {
                return RecordComparator.FOLLOWS;
            }
        } catch (Exception error) {
            error.printStackTrace();
            
            return RecordComparator.EQUIVALENT;
        }
    }
    
    public void compareClose() {
        try {
            if (comparatorInputStream != null) {
                comparatorInputStream.close();
            }
            
            if (comparatorInputDataType != null) {
                comparatorInputDataType.close();
            }
        } catch (Exception error) {
        }
    }
}
