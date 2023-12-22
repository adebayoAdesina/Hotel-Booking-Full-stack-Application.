import React, { useEffect, useState } from 'react'
import { GetRoomTypes } from '../utils/ApiFunctions'
import '../../index.css'

const RoomTypeSelector = ({handleRoomInputChange, newRoom}) => {
    const[roomTypes, setRoomTypes] = useState([])
    const[showRoomTypeInput, setShowRoomTypeInput] = useState(false)
    const[newRoomType, setNewRoomType] = useState("")

    useEffect(() => {
   GetRoomTypes().then((data)=> {
    setRoomTypes(data)
   })
    
    }, [])

    const handleNewRoomTypeInputChange = (e) => {
      setNewRoomType(e.target.value)
    }
    
    const handleAddNewRoomType = () => {
        if (newRoomType !== "") {
          setRoomTypes(...roomTypes, newRoomType)
          setNewRoomType()
          setShowRoomTypeInput(false)
        }
    }
  return (
    <>
      {roomTypes.length == 0 && (
        <div>
          <select name="roomType" id="roomType" className='form-select' value={newRoom.roomTypes} onChange={(e) => {
            if (e.target.value === "Add new") {
              setShowRoomTypeInput(true)
            } else {
              handleRoomInputChange(e)
              setShowRoomTypeInput(false)
            }
          }}>
            <option value={""}> Select a room Type</option>
            {
              roomTypes.map((type, index) => (
                <option key={index} value={type}>{type}</option>
              ))
            }
            <option value={"Add new"}>Add new</option>
          </select>
          {
            showRoomTypeInput && (
              <div className='input-group mt-2'>
                <input type="text" className="form-control" placeholder='Enter a new room type' onChange={handleNewRoomTypeInputChange}/>
                <button className="btn-hotel btn" type='button' onClick={handleAddNewRoomType}>Add</button>
              </div>
            )
          }
        </div>
      )}
    </>
  )
}

export default RoomTypeSelector