import { Component, Input, Output, EventEmitter, ViewChild, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Visite } from './visite.model';

@Component({
    selector: 'jhi-visite-edit',
    templateUrl: './visite-edit.component.html'
})
export class VisiteEditComponent implements OnInit {

    @Input() visite: Visite;
    @Output() valid = new EventEmitter<boolean>();
    @ViewChild('editForm') ngForm: NgForm;

    ngOnInit() {
        this.ngForm.form.valueChanges.subscribe(x => {
          this.sendValid(this.ngForm.valid);
        })
    }

    sendValid(val: boolean) {
        this.valid.emit(val);
    }
}
