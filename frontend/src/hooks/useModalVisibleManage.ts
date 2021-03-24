import React from 'react';

function useModalVisibleMange(): [boolean, () => void, () => void] {
    const [isModalOpen, setModalOpen] = React.useState<boolean>(false);

    const openModal = (): void => {
        setModalOpen(true);
    };

    const closeModal = (): void => {
        setModalOpen(false);
    };
    return [isModalOpen, openModal, closeModal];
}

export default useModalVisibleMange;
