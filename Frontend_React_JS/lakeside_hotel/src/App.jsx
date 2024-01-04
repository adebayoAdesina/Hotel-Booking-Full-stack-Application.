
import './App.css'
import AddRoom from './components/room/AddRoom'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Home from './components/home/Home'
import EditRoom from './components/room/EditRoom'
import ExistingRooms from './components/room/ExistingRooms'
import NavBar from './components/layout/NavBar'
import Footer from './components/layout/Footer'
import RoomListing from './components/room/RoomListing'
import Admin from './components/admin/Admin'
import CheckOut from './components/bookings/CheckOut'
import BookingSuccess from './components/bookings/BookingSuccess'

function App() {

  return (
    <>
      <main>
        <Router>
          <NavBar/>
          <Routes>
            <Route path='/' element={<Home/>} exact/>
            <Route path='/edit-room/:roomId' element={<EditRoom/>} exact/>
            <Route path='/existing-rooms' element={<ExistingRooms/>} exact/>
            <Route path='/add-room' element={<AddRoom/>} exact/>
            <Route path='/book-room/:roomId' element={<CheckOut/>} exact/>
            <Route path='/browse-all-rooms' element={<RoomListing/>} exact/>
            <Route path='/admin' element={<Admin/>} exact/>
            <Route path='/booking-success' element={<BookingSuccess/>} exact/>
          </Routes>
        </Router>
        <Footer/>
      </main>
    </>
  )
}

export default App
