package ua.com.foxminded.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rooms", schema = "uni")
public class Room {
    
    @Id
    @Column(name = "id_room", nullable = false)
    private String id;
    @Column(name = "name_room")
    private String name;
    
    public Room() {
      
    }

    public String getId() {
        return id;
    }
    public Room setId(String id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public Room setName(String name) {
        this.name = name;
        return this;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
