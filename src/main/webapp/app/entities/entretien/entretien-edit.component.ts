import { Component, Input, Output, EventEmitter, ViewChild, OnInit } from '@angular/core';

import { Entretien } from './entretien.model';
import { NgForm } from '@angular/forms';

@Component({
    selector: 'jhi-entretien-edit',
    templateUrl: './entretien-edit.component.html'
})
export class EntretienEditComponent implements OnInit {

    @Input() entretien: Entretien;
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
