package com.proaktivio.models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="token")
public class Token {
	 private static final int EXPIRATION = 60 * 24;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long id;

	    @Column(name="value", nullable = false)
	    private String value;

	    @Column(name="expiry_date", nullable = false)
	    private Date expiryDate;
	    
	    @OneToOne(fetch = FetchType.LAZY, 
				cascade = {CascadeType.PERSIST, CascadeType.MERGE})	
		@JoinColumn(name = "client_id", nullable = false)
	    private Client client;
	    
	    public Token() {
	        super();
	    }
	    public Token(String value) {
	        super();

	        this.value = value;
	        this.expiryDate = calculateExpiryDate(EXPIRATION);
	    }
	    public Token(String value, Client client) {
	        super();

	        this.value = value;
	        this.client = client;
	        this.expiryDate = calculateExpiryDate(EXPIRATION);
	    }
	    public Long getId() {
	        return id;
	    }
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Client getClient() {
			return client;
		}
		public void setClient(Client client) {
			this.client = client;
		}
		public Date getExpiryDate() {
	        return expiryDate;
	    }
	    public void setExpiryDate(Date expiryDate) {
	        this.expiryDate = expiryDate;
	    }
	    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
	        final Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(new Date().getTime());
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	    }
	    public void updateToken(String value) {
	        this.value = value;
	        this.expiryDate = calculateExpiryDate(EXPIRATION);
	    }
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
	        result = prime * result + ((value == null) ? 0 : value.hashCode());
	        result = prime * result + ((client == null) ? 0 : client.hashCode());
	        return result;
	    }
	    @Override
	    public boolean equals(final Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Token other = (Token) obj;
	        if (expiryDate == null) {
	            if (other.expiryDate != null) {
	                return false;
	            }
	        } else if (!expiryDate.equals(other.expiryDate)) {
	            return false;
	        }
	        if (value == null) {
	            if (other.value != null) {
	                return false;
	            }
	        } else if (!value.equals(other.value)) {
	            return false;
	        }
	        if (client == null) {
	            if (other.client != null) {
	                return false;
	            }
	        } else if (!client.equals(other.client)) {
	            return false;
	        }
	        return true;
	    }
	    @Override
	    public String toString() {
	        final StringBuilder builder = new StringBuilder();
	        builder.append("Token [Value=").append(value)
	        .append("]").append("[Expires").append(expiryDate).append("]");
	        return builder.toString();
	    }
}
