import React, { useState } from 'react'

const EditRoom = () => {
    const [room, setRoom] = useState({
        photo: null,
        roomType : "",
        roomPrice: "",
    })

    const [imagePreview, setImagePreview] = useState("")
    const [successMessage, setSuccessMessage] = useState("")
    const [errorMessage, setErrorMessage] = useState("")

    const handleImageChange =(e) => {
        const selectedImage = e.target.files[0];
        setRoom({...room, photo : selectedImage})
        setImagePreview(URL.createObjectURL(selectedImage))
    }

    const handleRoomInputChange =(e) => {
        const {name, value} = e.target;
        setRoom({...room, [name] : value})
        
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const success = await addRoom(newRoom.photo, newRoom.roomType, newRoom.roomPrice);
            if (success !== undefined) {
                setSuccessMessage("A new room was added to the database")
                setNewRoom({photo: null, roomType:"", roomPrice:""})
                setImagePreview("")
                setErrorMessage("")
            } else{
                setErrorMessage("Error adding room")
            }
        } catch (error) {
            setErrorMessage(error.message)
        }
        setTimeout(() => {
            setSuccessMessage("")
            setErrorMessage("")
        }, 3000)
    }


  return (
    <div>
        <h2>
            Edit Room
        </h2>
    </div>
  )
}

export default EditRoom