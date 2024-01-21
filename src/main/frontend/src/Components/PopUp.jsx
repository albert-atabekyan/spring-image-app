import React, { useState } from 'react';
import '../css/PopUp.css'; // You can style the modal in this CSS file

function PopUp(props) {
  const [showModal, setShowModal] = useState(false);
  const { children, openText} = props;
  const toggleModal = () => {
    setShowModal(!showModal);
  };

  return (
    <div className="popup">
      <button onClick={toggleModal}>{openText}</button>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            {children}
            <button onClick={toggleModal}>Закрыть окно</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default PopUp;