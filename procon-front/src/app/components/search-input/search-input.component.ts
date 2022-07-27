import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.css']
})
export class SearchInputComponent implements OnInit {
  @Input() form!: FormGroup;
  @Input() label: string = '';
  icon = faSearch;

  constructor() { }

  ngOnInit(): void {
  }

}
