/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.response;

import java.io.Serializable;

/**
 *
 * @author Hubert
 */
public class PermissionResponse implements Serializable {

    private String roleName;

    private int roleId;

    private boolean read;

    private boolean write;

    private boolean delete;

    private boolean edit;

    private boolean readOwn;

    private boolean deleteOwn;

    private boolean editOwn;

    public PermissionResponse() {
    }

    public PermissionResponse(int permission) {
        this.edit = (permission >> 3 & 1) == 1;
        this.delete = (permission >> 2 & 1) == 1;
        this.write = (permission >> 1 & 1) == 1;
        this.read = (permission & 1) == 1;
        this.editOwn = (permission >> 6 & 1) == 1 || this.edit;
        this.deleteOwn = (permission >> 5 & 1) == 1 || this.delete;
        this.readOwn = (permission >> 4 & 1) == 1 || this.read;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isReadOwn() {
        return readOwn;
    }

    public void setReadOwn(boolean readOwn) {
        this.readOwn = readOwn;
    }

    public boolean isDeleteOwn() {
        return deleteOwn;
    }

    public void setDeleteOwn(boolean deleteOwn) {
        this.deleteOwn = deleteOwn;
    }

    public boolean isEditOwn() {
        return editOwn;
    }

    public void setEditOwn(boolean editOwn) {
        this.editOwn = editOwn;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
