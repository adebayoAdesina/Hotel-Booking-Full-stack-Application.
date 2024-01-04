import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { bookRoom, getRoomById } from "../utils/ApiFunctions";
import moment from "moment";
import { Form, FormControl } from "react-bootstrap";
import BookingSummary from "./BookingSummary";

const BookingForm = () => {
  const [isValidated, setIsValidated] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [roomPrice, setRoomPrice] = useState(0);
  const [booking, setBooking] = useState({
    guestFullName: "",
    guestEmail: "",
    checkInDate: "",
    checkOutDate: "",
    numberOfAdults: "",
    numberOfChildren: "",
  });
  const [roomInfo, setRoomInfo] = useState({
    photo: "",
    roomType: "",
    roomPrice: "",
  });
  const { roomId } = useParams();
  const navigate = useNavigate();

  const handelInputChange = (e) => {
    const { name, value } = e.target;
    setBooking({ ...booking, [name]: value });
    setErrorMessage("");
  };

  const getRoomPriceById = async (roomId) => {
    try {
      const response = await getRoomById(roomId);
      setRoomPrice(response.roomPrice);
    } catch (error) {
      throw new Error(error);
    }
  };

  useEffect(() => {
    getRoomPriceById(roomId);
  }, [roomId]);

  const calculatePayment = () => {
    const checkInDate = moment(booking.checkInDate);
    const checkOutDate = moment(booking.checkOutdate);
    const diffInDays = checkOutDate.diff(checkInDate);
    const paymentPerDay = roomPrice ? roomPrice : 0;
    return diffInDays * paymentPerDay;
  };

  const isGuestCountValid = () => {
    const adultCount = parseInt(booking.numberOfAdults);
    const childrenCount = parseInt(booking.numberOfChildren);
    const totalCount = adultCount + childrenCount;
    return totalCount >= 1 && adultCount >= 1;
  };

  const isCheckOutDateValid = () => {
    console.log(`${moment(booking.checkOutDate)} ${(moment(booking.checkInDate))}`)
    if (
      !moment(booking.checkOutDate) .isSameOrAfter(moment(booking.checkInDate))
    ) {
      setErrorMessage("Check-out date must come before check in date");
      return false;
    } else {
      setErrorMessage("");
      return true;
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;
    // console.log(form.checkValidity() === false)
    // console.log(isGuestCountValid())
    console.log(!isCheckOutDateValid())
    if (
        form.checkValidity() === false ||
        !isGuestCountValid() ||
        !isCheckOutDateValid()
        ) {
            e.stopPropagation();
          
        } else {
      setIsSubmitted(true);
    }
    setIsValidated(true);
  };

  const handleBooking = async () => {
    try {
      const confirmationCode = await bookRoom(roomId, booking);
      setIsSubmitted(true);
      navigate("/", { state: { message: confirmationCode } });
    } catch (error) {
      setErrorMessage(error.message);
      navigate("/", { state: { error: errorMessage } });
    }
  };

  return (
    <>
      <div className="container mb-5">
        <div className="row">
          <div className="col-md-6">
            <div className="card card-body mt-5">
              <h4 className="card ard-title">Reserve Room</h4>
              <Form noValidate validated={isValidated} onSubmit={handleSubmit}>
                <Form.Group>
                  <Form.Label htmlFor="guestFullName">Full Name :</Form.Label>
                  <FormControl
                    required
                    type="text"
                    id="guestFullName"
                    name="guestFullName"
                    value={booking.guestFullName}
                    placeholder="Enter your full name"
                    onChange={handelInputChange}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter your fullname
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group>
                  <Form.Label htmlFor="guestEmail">Email :</Form.Label>
                  <FormControl
                    required
                    type="text"
                    id="guestEmail"
                    name="guestEmail"
                    value={booking.guestEmail}
                    placeholder="Enter valid Email"
                    onChange={handelInputChange}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter your email
                  </Form.Control.Feedback>
                </Form.Group>

                <fieldset style={{ border: "2px" }}>
                  <legend>Lodging period</legend>
                  <div className="row">
                    <div className="col-6">
                      <Form.Label htmlFor="checkInDate">
                        Check-In date :
                      </Form.Label>
                      <FormControl
                        required
                        type="date"
                        id="checkInDate"
                        name="checkInDate"
                        value={booking.checkInDate}
                        placeholder="Check-in date"
                        onChange={handelInputChange}
                      />
                      <Form.Control.Feedback type="invalid">
                        Please select a check-in-date
                      </Form.Control.Feedback>
                    </div>
                    <div className="col-6">
                      <Form.Label htmlFor="checkOutDate">
                        Check-Out date :
                      </Form.Label>
                      <FormControl
                        required
                        type="date"
                        id="checkOutDate"
                        name="checkOutDate"
                        value={booking.checkOutdate}
                        placeholder="Check-out date"
                        onChange={handelInputChange}
                      />
                      <Form.Control.Feedback type="invalid">
                        Please select a check-out-date
                      </Form.Control.Feedback>
                    </div>
                    {errorMessage && (
                      <p className="error-message text-danger">
                        {errorMessage}
                      </p>
                    )}
                  </div>
                </fieldset>

                <fieldset>
                  <legend>Number of Guest</legend>
                  <div className="row">
                    <div className="col-6">
                      <Form.Label htmlFor="numberOfAdults">Adults :</Form.Label>
                      <FormControl
                        required
                        type="number"
                        id="numberOfAdults"
                        name="numberOfAdults"
                        value={booking.numberOfAdults}
                        min={1}
                        placeholder="0"
                        onChange={handelInputChange}
                      />
                      <Form.Control.Feedback type="invalid">
                        Please select at least 1 adult
                      </Form.Control.Feedback>
                    </div>
                    <div className="col-6">
                      <Form.Label htmlFor="numberOfChildren">
                        Children :
                      </Form.Label>
                      <FormControl
                      
                        type="number"
                        id="numberOfChildren"
                        name="numberOfChildren"
                        value={booking.numberOfChildren}
                        placeholder="0"
                        onChange={handelInputChange}
                      />
                    </div>
                  </div>
                </fieldset>
                <div className="form-group mt-2 mb-2">
                  <button className="btn btn-hotel" type="submit">
                    Continue
                  </button>
                </div>
              </Form>
            </div>
          </div>
          <div className="col-md-6">
            {isSubmitted && (
              <BookingSummary
                booking={booking}
                payment={calculatePayment}
                isFormValid={isValidated}
                onConfirm={handleBooking}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default BookingForm;
