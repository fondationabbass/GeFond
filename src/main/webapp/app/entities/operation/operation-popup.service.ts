import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Operation } from './operation.model';
import { OperationService } from './operation.service';

@Injectable()
export class OperationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private operationService: OperationService

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
                this.operationService.find(id)
                    .subscribe((operationResponse: HttpResponse<Operation>) => {
                        const operation: Operation = operationResponse.body;
                        if (operation.dateOperation) {
                            operation.dateOperation = {
                                year: operation.dateOperation.getFullYear(),
                                month: operation.dateOperation.getMonth() + 1,
                                day: operation.dateOperation.getDate()
                            };
                        }
                        this.ngbModalRef = this.operationModalRef(component, operation);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.operationModalRef(component, new Operation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    operationModalRef(component: Component, operation: Operation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operation = operation;
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
