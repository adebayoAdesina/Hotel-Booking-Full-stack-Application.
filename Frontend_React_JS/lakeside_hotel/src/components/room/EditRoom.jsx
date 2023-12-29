import { useState, useEffect } from 'react'
import { getRoomById, updateRoom } from '../utils/ApiFunctions'
import { Link, useParams } from 'react-router-dom'
import RoomTypeSelector from '../common/RoomTypeSelector'


const EditRoom = () => {
    const [room, setRoom] = useState({
        photo: null,
        roomType : "",
        roomPrice: "",
    })

    const [imagePreview, setImagePreview] = useState("")
    const [successMessage, setSuccessMessage] = useState("")
    const [errorMessage, setErrorMessage] = useState("")

    const {roomId} = useParams()

    const handleImageChange =(e) => {
        const selectedImage = e.target.files[0];
        setRoom({...room, photo : selectedImage})
        setImagePreview(URL.createObjectURL(selectedImage))
    }

    const handleRoomInputChange =(e) => {
        const {name, value} = e.target;
        setRoom({...room, [name] : value})
        
    }

    useEffect(() => {
      const fetchRoom = async() => {
        try {
            const roomData = await getRoomById(roomId)
            setRoom(roomData);
            console.log(roomData)
            setImagePreview(roomData.photo)
          } catch (error) {
            console.error(error)
          }
      }
      fetchRoom()
    }, [roomId])
    

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const success = await updateRoom(roomId, room);
            if (success !== undefined) {
                setSuccessMessage("A new room was added to the database")
                setRoom({photo: null, roomType:"", roomPrice:""})
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
    <>
        <section className='continer mt-5 mb-5'> 
            <div className='row justify-content-center'>
                <div className='col-md-8 col-lg-6'>
                    <h2 className='mt-5 mb-2'>Add New Room</h2>
                    {
                        successMessage && (
                            <div className='alert alert-success fade show'>
                                {successMessage}
                            </div>
                        )
                    }
                    {
                        errorMessage && (
                            <div className='alert alert-dager fade show'>
                                {errorMessage}
                            </div>
                        )
                    }

                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label htmlFor="roomType" className="form-label">Room Type</label>
                            <div>
                                
                                <RoomTypeSelector handleRoomInputChange={handleRoomInputChange} newRoom={room}/>
                            </div>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="roomType" className="form-label">Room Price</label>
                            <input type="number" className="form-control" required id='roomPrice' name='roomPrice' value={room.roomPrice} onChange={handleRoomInputChange}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="roomType" className="form-label">Room Photo</label>
                            <input type="file" className="form-control" required id="photo" name='photo'  onChange={handleImageChange}/>
                        </div>
                        {imagePreview && (
                            <img src={imagePreview} alt="Preview room photo" style={{maxWidth
                            : "400px", maxHeight: "400px"}} className='mb-3'/>
                        )}
                        <div className='d-grid d-md-flex mt-2'>
                            <Link to={"/existing-rooms"} className='btn btn-outline-info ml-5'>
                                Back
                            </Link>
                            <button className="btn btn-outline-warning">Edit Room</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </>
  )
}

export default EditRoom