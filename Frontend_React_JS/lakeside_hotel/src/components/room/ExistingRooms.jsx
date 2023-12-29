import { useState, useEffect } from 'react'
import { deleteRoom, getAllRooms } from '../utils/ApiFunctions'
import { Col, Row } from 'react-bootstrap'
import RoomFilter from '../common/RoomFilter'
import { RoomPaginator } from '../common/RoomPaginator'
import {FaEdit, FaEye, FaPlus, FaTrashAlt} from 'react-icons/fa'
import { Link } from 'react-router-dom'

const ExistingRooms = () => {
    const[rooms, setRooms] = useState([])
    const[currectPage, setCurrentPage] = useState(1)
    const[roomsPerPage] = useState(8)
    const[isLoading, setIsLoading] = useState(false)
    const[filteredRooms, setFilteredRooms] = useState([])
    const[selectedRoomType, setSelectedRoomType] = useState("")
    const[successMessage, setSuccessMessage] = useState("")
    const[errorMessage, setErrorMessage] = useState("")

    useEffect(() => {
     fetchRooms()
    }, [])
    
    const fetchRooms = async() => {
        setIsLoading(true)
        try {
            const result = await getAllRooms();
            setRooms(result);
            setIsLoading(false)
           
            
        } catch (error) {
            setErrorMessage(error.message)
        }
    }

    useEffect(() => {
        
      if (selectedRoomType === "") {
        setFilteredRooms(rooms)
      } else {
        const filtered = rooms.filter((room) => room.roomType === selectedRoomType)
        
        setFilteredRooms(filtered);
      }
      setCurrentPage(1)
    
    }, [rooms,selectedRoomType])

    const calculateTotalPages = (filteredRooms, roomsPerPage, room) => {
        const totalRooms = filteredRooms.length > 0 ? filteredRooms.length : room.length
        return Math.ceil(totalRooms/roomsPerPage)
    }

    const indexOfLastRoom = currectPage * roomsPerPage; 
    const indexOfFirstRoom = indexOfLastRoom - roomsPerPage;
    const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom)
    
    const handlePaginationClick = (pageNumber) => {
        setCurrentPage(pageNumber)
    }

    const handleDelete = async(roomId) => {
        try {
            const result = await deleteRoom(roomId);
            if (result === "") {
                setSuccessMessage(`Room number ${roomId} was deleted`)
                fetchRooms()
            } else {
                console.error(`Error deleting : ${result.message}`)
            }
        } catch (error) {
            setErrorMessage(error.message)
        }
        setTimeout(()=>{
            setSuccessMessage("")
            setErrorMessage("")
        },3000);
    }
  return (
    <>
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
        {
            isLoading ? (
                <p>Loading existing rooms</p>
            ) :(
                <>
                    <section className='mt-5 mb-5 container-fluid'>
                        <div className="d-flex justify-content-between mb-3 mt-5">
                            <h2>Existing rooms</h2>
                           
                        </div> 
                        <Row>
                        <Col md={6} className='mb-3 mb-md-0'>
                            <RoomFilter data={rooms} setFilteredData={setFilteredRooms}/>
                        </Col>
                        <Col md={6} className='d-flex justify-content-end'>
                        <Link to={"/add-room"}>
                                <FaPlus/> Add New Room
                            </Link></Col>
                            </Row>
                        <table className='table table-bordered table-hover'>
                            <thead>
                                <tr className='text-center'>
                                    <th>ID</th>
                                    <th>Room Type</th>
                                    <th>Room Price</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentRooms.map((room, index) => (
                                    <tr key={index} className='text-center'>
                                        <td>{++index}</td>
                                        <td>{room.roomType}</td>
                                        <td>{room.roomPrice}</td>
                                        <td className='gap-2'>
                                            <Link to={`/edit-room/${room.id}`}>
                                                <span className="btn btn-info btn-sm">
                                                    <FaEye/>
                                                </span>
                                                <span className="btn btn-warning btn-sm">
                                                    <FaEdit/>
                                                </span>
                                            </Link>
                                            <button
                                            className='btn btn-danger btn-sm'
                                            onClick={()=> handleDelete(room.id)}
                                            >
                                                <FaTrashAlt/>
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        <RoomPaginator currentPage={currectPage} totalPages={calculateTotalPages(filteredRooms, roomsPerPage, rooms)} onPageChange={handlePaginationClick} />
                    </section>
                </>
            )
        }
    </>
  )
}

export default ExistingRooms