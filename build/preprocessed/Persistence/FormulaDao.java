/*
 * FormulaDao.java
 *
 * Created on March 13, 2006, 3:44 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package Persistence;

import javax.microedition.rms.*;
import java.io.*;
import java.util.Date;
/**
 *
 * @author user
 */
public class FormulaDao {
    private RecordStore recordstore = null;
    private RecordEnumeration recordEnumeration = null;
   
    /** Creates a new instance of FormulaDao */
    public FormulaDao() {
    }

    public boolean save(FormulaValue fv) {
        StartRecordOperations();
        
        boolean returnValue;
        try {
            byte[] outputRecord;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream outputDataStream = new DataOutputStream(outputStream);
            
            outputDataStream.writeUTF(fv.getName());
            outputDataStream.writeUTF(fv.getResult());
            outputDataStream.writeInt(fv.getPatientID());
            outputDataStream.writeInt(fv.getTimeStamp());
            outputDataStream.writeInt(recordstore.getNextRecordID());
            outputDataStream.flush();
            outputRecord = outputStream.toByteArray();
            recordstore.addRecord(outputRecord, 0, outputRecord.length);
            outputStream.reset();
            outputStream.close();
            outputDataStream.close();
            returnValue = true;
        } catch (Exception error) {
            returnValue = false;
        }
        EndRecordOperations();
        return returnValue;
    }
    
    public FormulaValue[] findAll() {
        StartRecordOperations();
        FormulaValue[] ret;
        try {
            ret = new FormulaValue[recordstore.getNumRecords()];    
        } catch (Exception e) {
            return new FormulaValue[0];
        }

        ByteArrayInputStream stream;
        DataInputStream reader;
        try {
            recordEnumeration = recordstore.enumerateRecords(
                null, new FormulaOrderByTimeDesc(), false
            );
            int counter = 0;
            while (recordEnumeration.hasNextElement()) {
                int nextRecordId = recordEnumeration.nextRecordId();
                int i = nextRecordId;
                byte[] rec = new byte[recordstore.getRecordSize(nextRecordId)];
                recordstore.getRecord(nextRecordId, rec, 0);
                stream = new ByteArrayInputStream(rec);
                reader = new DataInputStream(stream);

                FormulaValue fv = new FormulaValue(reader.readUTF(), reader.readUTF());
                fv.setPatientID(reader.readInt());
                fv.setTimeStamp(reader.readInt());
                fv.id = i;
                ret[counter++] = fv;
                stream.close();
            }
            
        } catch (Exception error) {
            recordEnumeration.destroy();
        }
        
        EndRecordOperations();
        return ret;
    }

    public FormulaValue[] findByPatientId(int patientId) {
        FormulaValue[] all = findAll();
        FormulaValue[] ret = new FormulaValue[all.length];
        
        int counter = 0;
        for (int i = 0; i < all.length; i++) {
            if (all[i].getPatientID() == patientId) {
                ret[counter] = all[i];
                counter++;
            }
        }
        
        if (counter > 0) {
            FormulaValue[] new_ret = new FormulaValue[counter];
            for (int i = 0; i < counter; i++) {
                new_ret[i] = ret[i];
            }
            ret = new_ret;
        }
        
        return ret;
    }
    
    public boolean delete(int formulaId) {
        StartRecordOperations();
        boolean retValue;
        try {
            recordstore.deleteRecord(formulaId);
            retValue = true;
        } catch (Exception e) {
            retValue = false;
        }
        EndRecordOperations();
        return retValue;
    }
    
    public String serialize(FormulaValue fv) {
        try {
            if (fv.getResult().trim() == "") {
                return "";
            }
            StringBuffer ret = new StringBuffer();
            ret.append("name=" + RemoteHelper.urlEncode(fv.getName()));
            ret.append("&result=" + RemoteHelper.urlEncode(fv.getResult()));
            ret.append("&patient_id=" + fv.getPatientID());
            ret.append("&timestamp=" + fv.getTimeStamp());
            ret.append("&id=" + fv.id);
            ret.append("&userEmail=" + RemoteHelper.getUserEmail());
            ret.append("&hashCode=" + RemoteHelper.getHashcode());
            return ret.toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    private void StartRecordOperations() {
        try {
            //RecordStore.deleteRecordStore("MCPDIS_Formula_DB");
            recordstore = RecordStore.openRecordStore("MCPDIS_Formula_DB", true);
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


class FormulaOrderByTimeDesc implements RecordComparator {
    private byte[] comparatorInputData;
    private ByteArrayInputStream comparatorInputStream = null;
    private DataInputStream comparatorInputDataType = null;
    
    public int compare(byte[] record1, byte[] record2) {
        int record1timestamp, record2timestamp;
        
        try {
            comparatorInputStream = new ByteArrayInputStream(record1);
            comparatorInputDataType = new DataInputStream(comparatorInputStream);
            String name = comparatorInputDataType.readUTF();
            String result = comparatorInputDataType.readUTF();
            comparatorInputDataType.readInt();
            comparatorInputDataType.readInt();
            record1timestamp = comparatorInputDataType.readInt();
            
            comparatorInputStream = new ByteArrayInputStream(record2);
            comparatorInputDataType = new DataInputStream(comparatorInputStream);
            name = comparatorInputDataType.readUTF();
            result = comparatorInputDataType.readUTF();
            comparatorInputDataType.readInt();
            comparatorInputDataType.readInt();
            record2timestamp = comparatorInputDataType.readInt();

            if (record1timestamp == record2timestamp) {
                return RecordComparator.EQUIVALENT;
            } else if (record1timestamp > record2timestamp) {
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
