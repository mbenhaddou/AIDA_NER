package aida.data;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class Entities implements Serializable, Iterable<Entity> {

  private static final long serialVersionUID = -5405018666688695438L;
  
    
  private TIntObjectHashMap<Entity> id2entity;

  private Set<Entity> entities = null;

  public Entities() {
    id2entity = new TIntObjectHashMap<Entity>(16, 0.75f, -1);
    entities = new HashSet<Entity>();
  }

  public Entities(Set<Entity> entities) {
    this.entities = entities;
    id2entity = new TIntObjectHashMap<Entity>(entities.size(), 1.0f, -1);
    for (Entity entity : entities) {
      id2entity.put(entity.getId(), entity);
    }
  }
  
  public Collection<Integer> getUniqueIds() {
    return Arrays.asList(ArrayUtils.toObject(id2entity.keys()));
  }
  
  public Collection<String> getUniqueNames() {
    Set<String> names = new HashSet<String>();
    for (Entity e : entities) {
      names.add(e.getIdentifierInKb());
    }
    return names;
  }
  
  public int[] getUniqueIdsAsArray() {
    return id2entity.keys();
  }

  public Set<Entity> getEntities() {
    return entities;
  }

  /**
   * Should only be used for testing or if you know the exact id for each entity
   * @param entity
   * @param id
   */
  public void add(Entity entity) {
    entities.add(entity);
    id2entity.put(entity.getId(), entity);
  }

  public void addAll(Entities entities) {
    this.entities.addAll(entities.entities);
    for(Entity e: entities) {
      id2entity.put(e.getId(), e);
    }
  }
  
  /**
   * Adds all entities to the collection. Make sure not to add duplicates!
   * 
   * @param entities
   */
  public void addAll(Collection<Entity> entities) {
    this.entities.addAll(entities);
    for(Entity e: entities) {
      id2entity.put(e.getId(), e);
    }
  }

  public int size() {
    return entities.size();
  }
  
  public boolean contains(int id) {
    return id2entity.containsKey(id);
  }

  
public Iterator<Entity> iterator() {
    return entities.iterator();
  }

  public boolean isEmpty() {
    return entities.isEmpty();
  }

  @Override
public String toString() {
    return entities.size() + " entities: " + StringUtils.join(entities, ", ");
  }
}
