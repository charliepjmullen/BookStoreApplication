package com.patterns.bookstore.model;

import javax.persistence.*; 

import com.patterns.bookstore.model.Book;

import java.util.List;
import java.util.Set;

@Entity
public class User {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String shipping_address;
    private String payment_details;
    private Set<Role> roles;
    private List<Book> shoppingCart;

    
    
    public User() {
		super();
	}

	public User(Long id, String username, String password, Set<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public User(Long id, String username, String password, String passwordConfirm) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public User(Long id, String username, String password, String passwordConfirm, String shipping_address,
			String payment_details, Set<Role> roles, List<Book> shoppingCart) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.shipping_address = shipping_address;
		this.payment_details = payment_details;
		this.roles = roles;
		this.shoppingCart = shoppingCart;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getPayment_details() {
		return payment_details;
	}

	public void setPayment_details(String payment_details) {
		this.payment_details = payment_details;
	}

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
	@OneToMany
	@JoinTable(name = "user_books", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	public List<Book> getShoppingCart(){
		return shoppingCart;
	}

	public void setShoppingCart(List<Book> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public void saveBookToShoppingCart(Book book) {
		shoppingCart.add(book);
	}
}
