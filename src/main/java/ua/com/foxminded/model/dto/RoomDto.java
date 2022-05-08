package ua.com.foxminded.model.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomDto {
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID id;
    private String name;
    
    public RoomDto() {
      
    }
    
    public RoomDto(RoomDto room) {
        this.name = room.name;
    }

    public UUID getId() {
        return id;
    }
    public RoomDto setId(UUID id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public RoomDto setName(String name) {
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
        RoomDto other = (RoomDto) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", name=" + name + "]";
    }      
}
