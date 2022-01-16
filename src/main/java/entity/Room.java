package entity;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class Room {

    private Integer roomNumber;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room() {
    }

    public Room(Integer roomId) {
        this.roomNumber = roomId;
    }
}
