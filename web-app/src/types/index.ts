export interface ILoading {
  isTable: boolean;
  isForm: boolean;
  isDelete: boolean;
}

/**
 * Interface for the ID
 * @param T - The type of the ID
 * for delete, update, and fetch by ID
 */
export interface IID<T> {
    fetchID: T;
    deleteID: T;
    updateID: T;
}

/**
 * Interface for modal
 */
export interface IModal {
  isOpen: boolean;
  openModal: () => void;
  closeModal: () => void;
}
