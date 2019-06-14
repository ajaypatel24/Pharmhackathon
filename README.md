# Pharmhackathon2018, 2nd Place Finish

This project was completed coded in Java as a submission for Pharmhackathon 2018. 
The time frame for creation was from 11am-8pm on 17-11-2018

# Function

The project is fully made in Java, it accepts a .FASTA file, and scans the genome
for mutated base pairs. Using several calculations, a probability of the mutation
leading to cancer is presented in a generated JTable

# Full Description

GenomeAI
"Health Is Wealth"

GenomeAI uses FASTA databases to compare a oncogenic sequence to patented and normal sequences to identify the regions of the oncogenic sequence that are most likely to cause cancer. 

These algorithms can be used for any number of genes in an effort to direct research in a user-friendly and quick fashion. 

Simply upload a FASTA file with an oncogenic sequence, normal sequences and patented sequences and click 'Run'

Of course, neural networks, chaos theory, lossless compression, machine learning and intelligent AI are implemented to run our algorithms. 

How does this get accomplished?
- Every sequence is determined to be either an oncogenic sequence, patented sequence, or normal sequence based on keywords in the header
- Every sequence is shortened to just to codons of interest (ie between start and stop codons)
- Every codon of oncogenic sequence is compared to each codon of normal sequences. If there is a mutation in oncogenic sequence that never appears in the normal sequences.. 
it is considered a potential mutation of interest and it's index (ie i-th codon of oncogonic sequence) is saved.
- Each potential mutation (codon) of interest from oncogenic sequence is compared to that codon of the patented sequence. 
- If a mutation in oncogenic sequence never occurs in patented sequence.. it is assigned a higher danger score than if an oncogenic mutation appears in multiple places
- Top 5 codon mutations are shown to user in a GUI.
