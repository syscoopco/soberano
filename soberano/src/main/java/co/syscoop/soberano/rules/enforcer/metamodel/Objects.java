package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Objects extends SimpleElement {

	//xml child elements
	private List<EntityType> entityTypes = new ArrayList<EntityType>();
	private List<ValueType> valueTypes = new ArrayList<ValueType>();
	private List<ObjectifiedType> objectifiedTypes = new ArrayList<ObjectifiedType>();
	
	//this list is used for iterating the objects
	private List<ObjectTypeElement> objectTypes = new ArrayList<ObjectTypeElement>();

	//this map is used for rapidly locating an object by its id
	private HashMap<String, ObjectTypeElement> objectTypeLocator = new HashMap<String, ObjectTypeElement>();
	
	//this map is used for rapidly locating the object type that plays a role. the key is the id of the role
	private HashMap<String, ObjectTypeElement> rolePlayerLocator = new HashMap<String, ObjectTypeElement>();
	
	//this map is useful for rapidly locating and objectified type by the id of its objectified fact type
	private HashMap<String, ObjectifiedType> objectifiedTypeLocator = new HashMap<String, ObjectifiedType>();
	
	//methods
	public HashMap<String, ObjectifiedType> getObjectifiedTypeLocator() {
		return objectifiedTypeLocator;
	}
	
	public void addRolePlayerToLocator(String roleId, ObjectTypeElement objectTypeElelemnt) {
		rolePlayerLocator.put(roleId, objectTypeElelemnt);
	}
	
	public HashMap<String, ObjectTypeElement> getRolePlayerLocator() {
		return rolePlayerLocator;
	}
	
	public List<ObjectTypeElement> getObjectTypes() {
		return objectTypes;
	}
	
	public HashMap<String, ObjectTypeElement> getObjectTypeLocator() {
		return objectTypeLocator;
	}
	
	private void updateAuxiliaryCollections(ObjectTypeElement objectTypeElement) throws Exception {
		
		try {
			//if it is an objectified type. could include binary non functional, ternary and objectified fact types
			if ((objectTypeElement.getClass()).getName().contains("ObjectifiedType")) {
				objectifiedTypeLocator.put(((ObjectifiedType) objectTypeElement).getNestedPredicates().get(0).getRef(), (ObjectifiedType) objectTypeElement);
			}
			
			//if it is a value type, entity type or objectification
			if (((objectTypeElement.getClass()).getName().contains("ValueType")) ||
					((objectTypeElement.getClass()).getName().contains("EntityType")) ||
					((objectTypeElement.getClass()).getName().contains("ObjectifiedType") &&
							((ObjectifiedType) objectTypeElement).getNestedPredicates().get(0).getIsImplied() == null)) {
				objectTypes.add(objectTypeElement);
				objectTypeLocator.put(objectTypeElement.getId(), objectTypeElement);
			}
			
			//if the object plays some role
			if (objectTypeElement.getPlayedRoless().size() > 0) {
				for (Role role : objectTypeElement.getPlayedRoless().get(0).getRoles()) {
					rolePlayerLocator.put(role.getRef(), objectTypeElement);
				}
				for (Role role : objectTypeElement.getPlayedRoless().get(0).getSubtypeMetaRoles()) {			
					rolePlayerLocator.put(role.getRef(), objectTypeElement);
				}
				for (Role role : objectTypeElement.getPlayedRoless().get(0).getSupertypeMetaRoles()) {
					rolePlayerLocator.put(role.getRef(), objectTypeElement);
				}
			}
		}
		catch(Exception cause) {
			cause.printStackTrace();
			throw new SoberanoException(cause);
		}
	}
	
	public void addEntityType(EntityType entityType) throws Exception {entityTypes.add(entityType); updateAuxiliaryCollections(entityType);}
	
    public List<EntityType> getEntityTypes() {return entityTypes;}
    
    public void addValueType(ValueType valueType) throws Exception {valueTypes.add(valueType); updateAuxiliaryCollections(valueType);}
	
    public List<ValueType> getValueTypes() {return valueTypes;}
    
    public void addObjectifiedType(ObjectifiedType objectifiedType) throws Exception {objectifiedTypes.add(objectifiedType); updateAuxiliaryCollections(objectifiedType);}
	
    public List<ObjectifiedType> getObjectifiedTypes() {return objectifiedTypes;}
}
