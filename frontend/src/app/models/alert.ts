export class Alert {
  id?: string;
  type?: AlertType;
  message?: string;
  autoClose?: boolean = true;
  keepAfterRouteChange?: boolean;
  fade?: boolean = true;
  show?: boolean = this.fade;

  constructor(init?: Partial<Alert>) {
    Object.assign(this, init);
    this.show = this.fade;
  }
}

export enum AlertType {
  Success,
  Error,
  Info,
  Warning,
}
