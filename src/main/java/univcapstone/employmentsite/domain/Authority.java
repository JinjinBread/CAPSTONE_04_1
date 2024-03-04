package univcapstone.employmentsite.domain;



public enum Authority {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private String role;

    Authority(String role) {
        this.role = role;
    }
}
