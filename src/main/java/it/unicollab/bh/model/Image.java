package it.unicollab.bh.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}


	@Lob
    private byte[] data;


    public Image(String name, String type, byte[] data)
    {
        this.name = name;
        this.type = type;
        this.data = data;
    }


}