import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Client } from './client.model';
import { ClientService } from './client.service';

@Injectable()
export class ClientPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clientService: ClientService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.clientService.find(id)
                    .subscribe((clientResponse: HttpResponse<Client>) => {
                        const client: Client = clientResponse.body;
                        if (client.dateCreat) {
                            client.dateCreat = {
                                year: client.dateCreat.getFullYear(),
                                month: client.dateCreat.getMonth() + 1,
                                day: client.dateCreat.getDate()
                            };
                        }
                        if (client.dateMaj) {
                            client.dateMaj = {
                                year: client.dateMaj.getFullYear(),
                                month: client.dateMaj.getMonth() + 1,
                                day: client.dateMaj.getDate()
                            };
                        }
                        this.ngbModalRef = this.clientModalRef(component, client);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.clientModalRef(component, new Client());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    clientModalRef(component: Component, client: Client): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.client = client;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}