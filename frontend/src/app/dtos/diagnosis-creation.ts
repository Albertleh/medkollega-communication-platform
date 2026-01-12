export class DiagnosisCreationDto {
  constructor(
    public diagnosisText: string,
    public icdCode: string | null,
    public validUntil: string | null   // LocalDate
  ) {}
}
