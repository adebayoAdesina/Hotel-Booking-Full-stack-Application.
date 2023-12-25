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
};

// This function get all room type from database
export const getRoomTypes = async () => {
    try {
        const response = await api.get("/rooms/room-types")
        console.log(response.data)
        return response.data;
    } catch (error) {
        throw new  Error("Error fetching room")
        // throw new Error(error)
    }
}