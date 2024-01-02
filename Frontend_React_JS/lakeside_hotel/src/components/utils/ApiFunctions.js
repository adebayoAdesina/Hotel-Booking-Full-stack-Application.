import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:9091/api/v1"
})

// This function add new room type from database
export async function addRoom(photo, roomType, roomPrice) {
    const formData = new FormData();
    formData.append("photo", photo);
    formData.append("room_type", roomType);
    formData.append("room_price", roomPrice);

    const response = await api.post("/room/add/new-room", formData)
    if (response.status === 201) {
        return true
    } else {
        return false;
    }
}

// This function get all room type from database
export const getRoomTypes = async () => {
    try {
        const response = await api.get("/rooms/room-types")
        return response.data;
    } catch (error) {
        throw new  Error("Error fetching room")
    }
}

//For getting all rooms
export const getAllRooms = async () => {
    try {
        const result = await api.get("/rooms/all-rooms")
        return result.data
    } catch (error) {
        throw new  Error("Error fetching room")
    }
}

//For deleting Room by Id
export const deleteRoom = async (roomId) => {
    try {
        const result = await api.delete(`/rooms/delete/room/${roomId}`)
        return result.data;
    } catch (error) {
        throw new Error(`An error occur ${error.message}`)
    }
}

export const updateRoom = async (roomId, roomData) => {
    const formData = new FormData();
    formData.append("photo", roomData.photo);
    formData.append("room_type", roomData.roomType);
    formData.append("room_price", roomData.roomPrice);
    const response = await api.put(`/rooms/update/${roomId}`, formData)
    return response;
}

export const getRoomById = async(roomId) => {
    try {
        const result = await api.get(`/rooms/room/${roomId}`)
        return result.data
    } catch (error) {
        throw new Error('Error fetching data')
    }
}