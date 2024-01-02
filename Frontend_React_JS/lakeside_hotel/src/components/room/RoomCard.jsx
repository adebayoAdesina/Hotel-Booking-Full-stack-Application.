import { Card, Col } from "react-bootstrap"
import { Link } from "react-router-dom"

const RoomCard = ({room}) => {


  return (
    <Col className="mb-4" xs={12} key={room.id}>
    <Card className="p-2">
        <Card.Body className="d-flex flex-wrap align-items-center p-0">
            <div className="flex-shrink-0 mr-3 mb-3 mb-md-0">
                <Card.Img varient="top" src={`data:image/png;base64, ${room.photo}`}
                alt= 'Room Photo' style={{width: "100%", maxWidth: "200px", height: "auto"}}/>
            </div>
            <div className="flex-grow-1 ml-3 px-5 text-start">
                <Card.Title className="hotel-color">{room.roomType}</Card.Title>
                <Card.Title className="room-price">{room.roomPrice}</Card.Title>
                <Card.Text>Some room information goes here for the guest to read through</Card.Text>
            </div>
            <div className="flex-shrink-0 mt-3">
                <Link to={`bookings/${room.id}`} className="btn btn-hotel btn-sm">
                    Book Now
                </Link>
            </div>
        </Card.Body>
    </Card>

    </Col>
  )
}

export default RoomCard