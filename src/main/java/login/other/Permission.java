/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.other;

/**
 *
 * @author Hubert
 */
public enum Permission {
    READ(1),
    WRITE(2),
    DELETE(4),
    EDIT(8),
    READ_OWN(16),
    DELETE_OWN(32),
    EDIT_OWN(64),
    ADMIN(128);

    private final int number;

    private Permission(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
