import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Entretien } from './entretien.model';
import { EntretienService } from './entretien.service';

@Injectable()
export class EntretienPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private entretienService: EntretienService

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
                this.entretienService.find(id)
                    .subscribe((entretienResponse: HttpResponse<Entretien>) => {
                        const entretien: Entretien = entretienResponse.body;
                        if (entretien.dateEntretien) {
                            entretien.dateEntretien = {
                                year: entretien.dateEntretien.getFullYear(),
                                month: entretien.dateEntretien.getMonth() + 1,
                                day: entretien.dateEntretien.getDate()
                            };
                        }
                        this.ngbModalRef = this.entretienModalRef(component, entretien);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.entretienModalRef(component, new Entretien());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    entretienModalRef(component: Component, entretien: Entretien): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entretien = entretien;
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
