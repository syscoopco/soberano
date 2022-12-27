package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class DataTypes extends SimpleElement {

	//xml child elements
	private List<VariableLengthTextDataType> variableLengthTextDataTypes = new ArrayList<VariableLengthTextDataType>();
	private List<DateTemporalDataType> dateTemporalDataTypes = new ArrayList<DateTemporalDataType>();
	private List<FixedLengthTextDataType> fixedLengthTextDataTypes = new ArrayList<FixedLengthTextDataType>();
	private List<UnsignedIntegerNumericDataType> unsignedIntegerNumericDataTypes = new ArrayList<UnsignedIntegerNumericDataType>();
	private List<UnsignedTinyIntegerNumericDataType> unsignedTinyIntegerNumericDataTypes = new ArrayList<UnsignedTinyIntegerNumericDataType>();
	private List<TrueOrFalseLogicalDataType> trueOrFalseLogicalDataTypes = new ArrayList<TrueOrFalseLogicalDataType>();
	private List<AutoCounterNumericDataType> autoCounterNumericDataTypes = new ArrayList<AutoCounterNumericDataType>();
	private List<DoublePrecisionFloatingPointNumericDataType> doublePrecisionFloatingPointNumericDataTypes = new ArrayList<DoublePrecisionFloatingPointNumericDataType>();
	private List<MoneyNumericDataType> moneyNumericDataTypes = new ArrayList<MoneyNumericDataType>();
	private List<DateAndTimeTemporalDataType> dateAndTimeTemporalDataTypes = new ArrayList<DateAndTimeTemporalDataType>();
	private List<PictureRawDataDataType> pictureRawDataDataTypes = new ArrayList<PictureRawDataDataType>();
	private List<VariableLengthRawDataDataType> variableLengthRawDataDataTypes = new ArrayList<VariableLengthRawDataDataType>();
	private List<TimeTemporalDataType> timeTemporalDataTypes = new ArrayList<TimeTemporalDataType>();
	private List<SignedIntegerNumericDataType> signedIntegerNumericDataTypes = new ArrayList<SignedIntegerNumericDataType>();
	private List<ObjectIdOtherDataType> objectIdOtherDataTypes = new ArrayList<ObjectIdOtherDataType>();
	private List<SignedSmallIntegerNumericDataType> signedSmallIntegerNumericDataTypes = new ArrayList<SignedSmallIntegerNumericDataType>();
	private List<SignedLargeIntegerNumericDataType> signedLargeIntegerNumericDataTypes = new ArrayList<SignedLargeIntegerNumericDataType>();
	private List<SinglePrecisionFloatingPointNumericDataType> singlePrecisionFloatingPointNumericDataTypes = new ArrayList<SinglePrecisionFloatingPointNumericDataType>();
	private List<LargeLengthTextDataType> largeLengthTextDataTypes = new ArrayList<LargeLengthTextDataType>();
	private List<UnsignedSmallIntegerNumericDataType> unsignedSmallIntegerNumericDataTypes = new ArrayList<UnsignedSmallIntegerNumericDataType>();
	private List<DecimalNumericDataType> decimalNumericDataTypes = new ArrayList<DecimalNumericDataType>();
	private List<AutoTimestampTemporalDataType> autoTimestampTemporalDataTypes = new ArrayList<AutoTimestampTemporalDataType>();
	private List<UnsignedLargeIntegerNumericDataType> unsignedLargeIntegerNumericDataTypes = new ArrayList<UnsignedLargeIntegerNumericDataType>();
	private List<LargeLengthRawDataDataType> largeLengthRawDataDataTypes = new ArrayList<LargeLengthRawDataDataType>();
		
	//this map is used for rapidly locating a data type by its id
	private HashMap<String, DataTypeElement> dataTypeLocator = new HashMap<String, DataTypeElement>();
	
	//methods
	public HashMap<String, DataTypeElement> getDataTypeLocator() {
		return dataTypeLocator;
	}
	
	private void updateAuxiliaryCollections(DataTypeElement dataTypeElement) {
		dataTypeLocator.put(dataTypeElement.getId(), dataTypeElement);
	}
	
	public void addLargeLengthRawDataDataType(LargeLengthRawDataDataType largeLengthRawDataDataType) {largeLengthRawDataDataTypes.add(largeLengthRawDataDataType); updateAuxiliaryCollections(largeLengthRawDataDataType);}
	
    public List<LargeLengthRawDataDataType> getLargeLengthRawDataDataTypes() {return largeLengthRawDataDataTypes;}
	
	public void addUnsignedLargeIntegerNumericDataType(UnsignedLargeIntegerNumericDataType unsignedLargeIntegerNumericDataType) {unsignedLargeIntegerNumericDataTypes.add(unsignedLargeIntegerNumericDataType); updateAuxiliaryCollections(unsignedLargeIntegerNumericDataType);}
	
    public List<UnsignedLargeIntegerNumericDataType> getUnsignedLargeIntegerNumericDataTypes() {return unsignedLargeIntegerNumericDataTypes;}
	
	public void addAutoTimestampTemporalDataType(AutoTimestampTemporalDataType autoTimestampTemporalDataType) {autoTimestampTemporalDataTypes.add(autoTimestampTemporalDataType); updateAuxiliaryCollections(autoTimestampTemporalDataType);}
	
    public List<AutoTimestampTemporalDataType> getAutoTimestampTemporalDataTypes() {return autoTimestampTemporalDataTypes;}
	
	public void addDecimalNumericDataType(DecimalNumericDataType decimalNumericDataType) {decimalNumericDataTypes.add(decimalNumericDataType); updateAuxiliaryCollections(decimalNumericDataType);}
	
    public List<DecimalNumericDataType> getDecimalNumericDataTypes() {return decimalNumericDataTypes;}
		
	public void addUnsignedSmallIntegerNumericDataType(UnsignedSmallIntegerNumericDataType unsignedSmallIntegerNumericDataType) {unsignedSmallIntegerNumericDataTypes.add(unsignedSmallIntegerNumericDataType); updateAuxiliaryCollections(unsignedSmallIntegerNumericDataType);}
	
    public List<UnsignedSmallIntegerNumericDataType> getUnsignedSmallIntegerNumericDataTypes() {return unsignedSmallIntegerNumericDataTypes;}
	
	public void addLargeLengthTextDataType(LargeLengthTextDataType largeLengthTextDataType) {largeLengthTextDataTypes.add(largeLengthTextDataType); updateAuxiliaryCollections(largeLengthTextDataType);}
	
    public List<LargeLengthTextDataType> getLargeLengthTextDataTypes() {return largeLengthTextDataTypes;}
	
	public void addVariableLengthTextDataType(VariableLengthTextDataType variableLengthTextDataType) {variableLengthTextDataTypes.add(variableLengthTextDataType); updateAuxiliaryCollections(variableLengthTextDataType);}
	
    public List<VariableLengthTextDataType> getVariableLengthTextDataTypes() {return variableLengthTextDataTypes;}
    
    public void addDateTemporalDataType(DateTemporalDataType dateTemporalDataType) {dateTemporalDataTypes.add(dateTemporalDataType); updateAuxiliaryCollections(dateTemporalDataType);}
	
    public List<DateTemporalDataType> getDateTemporalDataTypes() {return dateTemporalDataTypes;}
    
    public void addFixedLengthTextDataType(FixedLengthTextDataType fixedLengthTextDataType) {fixedLengthTextDataTypes.add(fixedLengthTextDataType); updateAuxiliaryCollections(fixedLengthTextDataType);}
	
    public List<FixedLengthTextDataType> getFixedLengthTextDataTypes() {return fixedLengthTextDataTypes;}
    
    public void addUnsignedIntegerNumericDataType(UnsignedIntegerNumericDataType unsignedIntegerNumericDataType) {unsignedIntegerNumericDataTypes.add(unsignedIntegerNumericDataType); updateAuxiliaryCollections(unsignedIntegerNumericDataType);}
	
    public List<UnsignedIntegerNumericDataType> getUnsignedIntegerNumericDataTypes() {return unsignedIntegerNumericDataTypes;}
    
    public void addSignedIntegerNumericDataType(SignedIntegerNumericDataType signedIntegerNumericDataType) {signedIntegerNumericDataTypes.add(signedIntegerNumericDataType); updateAuxiliaryCollections(signedIntegerNumericDataType);}
	
    public List<SignedIntegerNumericDataType> getSignedIntegerNumericDataTypes() {return signedIntegerNumericDataTypes;}
    
    public void addUnsignedTinyIntegerNumericDataType(UnsignedTinyIntegerNumericDataType unsignedTinyIntegerNumericDataType) {unsignedTinyIntegerNumericDataTypes.add(unsignedTinyIntegerNumericDataType); updateAuxiliaryCollections(unsignedTinyIntegerNumericDataType);}
	
    public List<UnsignedTinyIntegerNumericDataType> getUnsignedTinyIntegerNumericDataTypes() {return unsignedTinyIntegerNumericDataTypes;}
    
    public void addTrueOrFalseLogicalDataType(TrueOrFalseLogicalDataType trueOrFalseLogicalDataType) {trueOrFalseLogicalDataTypes.add(trueOrFalseLogicalDataType); updateAuxiliaryCollections(trueOrFalseLogicalDataType);}
	
    public List<TrueOrFalseLogicalDataType> getTrueOrFalseLogicalDataTypes() {return trueOrFalseLogicalDataTypes;}
    
    public void addAutoCounterNumericDataType(AutoCounterNumericDataType autoCounterNumericDataType) {autoCounterNumericDataTypes.add(autoCounterNumericDataType); updateAuxiliaryCollections(autoCounterNumericDataType);}
	
    public List<AutoCounterNumericDataType> getAutoCounterNumericDataTypes() {return autoCounterNumericDataTypes;}
    
    public void addDoublePrecisionFloatingPointNumericDataType(DoublePrecisionFloatingPointNumericDataType doublePrecisionFloatingPointNumericDataType) {doublePrecisionFloatingPointNumericDataTypes.add(doublePrecisionFloatingPointNumericDataType); updateAuxiliaryCollections(doublePrecisionFloatingPointNumericDataType);}
	
    public List<DoublePrecisionFloatingPointNumericDataType> getDoublePrecisionFloatingPointNumericDataTypes() {return doublePrecisionFloatingPointNumericDataTypes;}
    
    public void addMoneyNumericDataType(MoneyNumericDataType moneyNumericDataType) {moneyNumericDataTypes.add(moneyNumericDataType); updateAuxiliaryCollections(moneyNumericDataType);}
	
    public List<MoneyNumericDataType> getMoneyNumericDataTypes() {return moneyNumericDataTypes;}
    
    public void addDateAndTimeTemporalDataType(DateAndTimeTemporalDataType dateAndTimeTemporalDataType) {dateAndTimeTemporalDataTypes.add(dateAndTimeTemporalDataType); updateAuxiliaryCollections(dateAndTimeTemporalDataType);}
	
    public List<DateAndTimeTemporalDataType> getDateAndTimeTemporalDataTypes() {return dateAndTimeTemporalDataTypes;}
    
    public void addPictureRawDataDataType(PictureRawDataDataType pictureRawDataDataType) {pictureRawDataDataTypes.add(pictureRawDataDataType); updateAuxiliaryCollections(pictureRawDataDataType);}
	
    public List<PictureRawDataDataType> getPictureRawDataDataTypes() {return pictureRawDataDataTypes;}
    
    public void addVariableLengthRawDataDataType(VariableLengthRawDataDataType variableLengthRawDataDataType) {variableLengthRawDataDataTypes.add(variableLengthRawDataDataType); updateAuxiliaryCollections(variableLengthRawDataDataType);}
	
    public List<VariableLengthRawDataDataType> getVariableLengthRawDataDataTypes() {return variableLengthRawDataDataTypes;}
    
    public void addTimeTemporalDataType(TimeTemporalDataType timeTemporalDataType) {timeTemporalDataTypes.add(timeTemporalDataType); updateAuxiliaryCollections(timeTemporalDataType);}
	
    public List<TimeTemporalDataType> getTimeTemporalDataTypes() {return timeTemporalDataTypes;}
    
	public void addObjectIdOtherDataType(ObjectIdOtherDataType objectIdOtherDataType) {objectIdOtherDataTypes.add(objectIdOtherDataType); updateAuxiliaryCollections(objectIdOtherDataType);}
    
	public List<ObjectIdOtherDataType> getObjectIdOtherDataTypes() {return objectIdOtherDataTypes;}
	
	public void addSignedSmallIntegerNumericDataType(SignedSmallIntegerNumericDataType signedSmallIntegerNumericDataType) {signedSmallIntegerNumericDataTypes.add(signedSmallIntegerNumericDataType); updateAuxiliaryCollections(signedSmallIntegerNumericDataType);}
	
    public List<SignedSmallIntegerNumericDataType> getSignedSmallIntegerNumericDataTypes() {return signedSmallIntegerNumericDataTypes;}

    public void addSignedLargeIntegerNumericDataType(SignedLargeIntegerNumericDataType signedLargeIntegerNumericDataType) {signedLargeIntegerNumericDataTypes.add(signedLargeIntegerNumericDataType); updateAuxiliaryCollections(signedLargeIntegerNumericDataType);}
	
    public List<SignedLargeIntegerNumericDataType> getSignedLargeIntegerNumericDataTypes() {return signedLargeIntegerNumericDataTypes;}

    
    public void addSinglePrecisionFloatingPointNumericDataType(SinglePrecisionFloatingPointNumericDataType singlePrecisionFloatingPointNumericDataType) {singlePrecisionFloatingPointNumericDataTypes.add(singlePrecisionFloatingPointNumericDataType); updateAuxiliaryCollections(singlePrecisionFloatingPointNumericDataType);}
	
    public List<SinglePrecisionFloatingPointNumericDataType> getSinglePrecisionFloatingPointNumericDataTypes() {return singlePrecisionFloatingPointNumericDataTypes;}
}
